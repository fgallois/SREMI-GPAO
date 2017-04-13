package fr.sremi.services;

import fr.sremi.dao.*;
import fr.sremi.data.OrderData;
import fr.sremi.data.OrderDetailData;
import fr.sremi.data.invoice.InvoiceData;
import fr.sremi.data.invoice.ReceiptData;
import fr.sremi.exception.ExcelException;
import fr.sremi.model.*;
import fr.sremi.util.InvoiceUtils;
import fr.sremi.vo.Command;
import fr.sremi.vo.ItemCommand;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by fgallois on 9/6/15.
 */
@Component
public class OrderService {
    private final Logger logger = Logger.getLogger(OrderService.class.getName());

    @Resource
    ConfigurationService configurationService;

    @Resource
    ExcelParserService excelParserService;

    @Resource
    OrderRepository orderRepository;

    @Resource
    OrderLineItemRepository orderLineItemRepository;

    @Resource
    PartRepository partRepository;

    @Resource
    BuyerRepository buyerRepository;

    @Resource
    ReceiptRepository receiptRepository;

    @Resource
    ClientRepository clientRepository;

    public List<OrderData> importOrders() {
        List<Command> commands = StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .flatMap(client -> {
                    try {
                        List<Command> clientCommands = excelParserService.getClientCommands(client);
                        persistCommands(clientCommands, client);
                        return clientCommands.stream();
                    } catch (ExcelException e) {
                        logger.error("Failed importing commands for client: " + client.getName(), e);
                        return null;
                    }
                })
                .collect(Collectors.toList());

        return commands.stream()
                .sorted(Comparator.comparing(Command::getReference))
                .map(command -> new OrderData(command.getReference()))
                .collect(Collectors.toList());
    }

    private void persistCommands(List<Command> commands, Client client) {
        for (Command command : commands) {
            Order order = orderRepository.findByReference(command.getReference());
            if (order == null) {
                order = new Order(command.getReference());
                for (ItemCommand itemCommand : command.getItems()) {
                    Part part = partRepository.findByReference(itemCommand.getItem().getReference());
                    if (part == null) {
                        part = new Part(itemCommand.getItem().getReference(), itemCommand.getItem()
                                .getDescription());
                        partRepository.save(part);
                    }
                    LineItem lineItem = new LineItem(itemCommand.getLine(), part, itemCommand.getQuantity(),
                            itemCommand.getDueDate());
                    order.addLineItem(lineItem);
                }
            } else {
                List<LineItem> lineItems = order.getLineItems();
                for (LineItem lineItem : lineItems) {
                    boolean found = false;
                    for (ItemCommand itemCommand : command.getItems()) {
                        if (itemCommand.getItem().getReference().compareTo(lineItem.getPart().getReference()) == 0) {
                            lineItem.setDueDate(itemCommand.getDueDate());
                            found = true;
                        }
                    }
                    if (!found) {
                        // Line item has been delivered
                        lineItem.setDelivered(Boolean.TRUE);
                    }
                }
                for (ItemCommand itemCommand : command.getItems()) {
                    boolean found = false;
                    for (LineItem lineItem : lineItems) {
                        if (lineItem.getPart().getReference().compareTo(itemCommand.getItem().getReference()) == 0) {
                            found = true;
                        }
                    }
                    if (!found) {
                        Part part = partRepository.findByReference(itemCommand.getItem().getReference());
                        if (part == null) {
                            part = new Part(itemCommand.getItem().getReference(), itemCommand.getItem()
                                    .getDescription());
                            partRepository.save(part);
                        }
                        LineItem lineItem = new LineItem(itemCommand.getLine(), part, itemCommand.getQuantity(),
                                itemCommand.getDueDate());
                        order.addLineItem(lineItem);
                    }
                }
            }
            order.setClient(client);
            order.setBuyer(buyerRepository.findByCode(command.getReference().substring(0, 2)));
            orderRepository.save(order);
        }
    }

    public List<OrderDetailData> getOrderDetails(String orderRef) {
        Order order = orderRepository.findByReference(orderRef);
        if (order != null) {
            return orderRepository.findByReference(orderRef).getLineItems().stream()
                    .filter(lineItem -> lineItem.getDelivered() == null || !lineItem.getDelivered())
                    .map(OrderDetailData::new)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void saveOrderReceipt(String orderRef, List<OrderDetailData> commands, int invoiceNumber) {
        Order order = orderRepository.findByReference(orderRef);
        if (order != null) {
            List<LineItem> lineItems = new ArrayList<>();
            for (OrderDetailData command : commands) {
                for (LineItem lineItem : order.getLineItems()) {
                    if (lineItem.getLine() == command.getLine()) {
                        lineItems.add(lineItem);
                    }
                }
            }
            Receipt receipt = new Receipt(invoiceNumber, lineItems);

            order.addReceipt(receipt);
            orderRepository.save(order);
        }
    }

    public List<OrderData> getOpenOrders() {
        return orderRepository.findByOpenTrueOrderByReferenceAsc().stream()
                .map(order -> new OrderData(order.getId(), order.getReference()))
                .collect(Collectors.toList());
    }

    public InvoiceData getInvoiceData(final String orderRef) {
        InvoiceData result = new InvoiceData();
        Order order = orderRepository.findByReference(orderRef);
        if (order != null) {
            result.setReference(order.getReference());
            result.setCertificateNumber(order.getClient().getCertificateNumber());
            result.setWithVat(order.getClient().isWithVat());
            result.setVatRate(configurationService.getVatRate());

            result.setReceipts(order.getReceipts().stream()
                    .filter(receipt -> InvoiceUtils.isForCurrentInvoice(receipt.getCreationDate()))
                    .map(receipt -> {
                        ReceiptData receiptData = new ReceiptData();
                        receiptData.setId(receipt.getId());
                        receiptData.setNumber(receipt.getNumber());
                        receiptData.setCreationDate(receipt.getCreationDate());

                        receiptData.setOrderDetails(receipt.getLineItems().stream()
                                .map(OrderDetailData::new)
                                .collect(Collectors.toList()));
                        return receiptData;
                    })
                    .collect(Collectors.toList()));
        }
        return result;
    }

    public void updateLineItemPrice(OrderDetailData orderDetailData) {
        LineItem lineItem = orderLineItemRepository.findOne(orderDetailData.getId());

        if (lineItem != null) {
            lineItem.setUnitPrice(orderDetailData.getUnitPriceHT());
            orderLineItemRepository.save(lineItem);
        }
    }

    public void updateInvoiceDate(String orderRef) {
        Order order = orderRepository.findByReference(orderRef);
        order.setInvoiceDate(InvoiceUtils.currentInvoiceDate().toDate());
        orderRepository.save(order);
    }

    public InvoiceData removeReceiptFromOrder(String commandeRef, String receiptRef) {
        Order order = orderRepository.findByReference(commandeRef);
        if (order != null) {
            List<Receipt> updatedReceipts = new ArrayList<>();
            Receipt receiptToDelete = null;
            for (Receipt receipt : order.getReceipts()) {
                if (receipt.getId().equals(Long.valueOf(receiptRef))) {
                    receiptToDelete = receipt;
                } else {
                    updatedReceipts.add(receipt);
                }
            }
            order.setReceipts(updatedReceipts);
            orderRepository.save(order);

            if (receiptToDelete != null) {
                receiptToDelete.getLineItems().clear();
                receiptRepository.save(receiptToDelete);
                receiptRepository.delete(receiptToDelete);
            }
        }
        return getInvoiceData(commandeRef);
    }
}
