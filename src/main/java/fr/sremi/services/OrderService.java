package fr.sremi.services;

import fr.sremi.dao.*;
import fr.sremi.data.OrderData;
import fr.sremi.data.OrderDetailData;
import fr.sremi.data.invoice.InvoiceData;
import fr.sremi.data.invoice.ReceiptData;
import fr.sremi.exception.ExcelException;
import fr.sremi.model.LineItem;
import fr.sremi.model.Order;
import fr.sremi.model.Part;
import fr.sremi.model.Receipt;
import fr.sremi.util.InvoiceUtils;
import fr.sremi.vo.Command;
import fr.sremi.vo.ItemCommand;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgallois on 9/6/15.
 */
@Component
public class OrderService {

    @Resource
    ConfigurationService configurationService;

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

    public List<OrderData> importOrders() throws ExcelException {
        File excelFile = new File(configurationService.getExcelPath());
        List<Command> commands = new ExcelParserService().getCommandsFromExcelFile(excelFile);
        persistCommands(commands);

        return commands.stream()
                .map(command -> new OrderData(null, command.getReference()))
                .collect(Collectors.toList());

    }

    private void persistCommands(List<Command> commands) {
        for (Command command : commands) {
            if (orderRepository.findByReference(command.getReference()) == null) {
                Order order = new Order(command.getReference());
                order.setBuyer(buyerRepository.findByCode(command.getReference().substring(0, 2)));
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
                orderRepository.save(order);
            }
        }
    }

    public List<OrderDetailData> getOrderDetails(String orderRef) {
        Order order = orderRepository.findByReference(orderRef);
        return order.getLineItems().stream()
                .map(lineItem -> new OrderDetailData(lineItem))
                .collect(Collectors.toList());
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
        List<OrderData> result = new ArrayList<>();

        List<Order> orders = orderRepository.findByOpenTrueOrderByReferenceAsc();
        for (Order order : orders) {
            result.add(new OrderData(order.getId(), order.getReference()));
        }
        return result;
    }

    public InvoiceData getInvoiceData(final String orderRef) {
        InvoiceData result = new InvoiceData();

        Order order = orderRepository.findByReference(orderRef);

        if (order != null) {
            result.setReference(order.getReference());
            result.setCertificateNumber(configurationService.getCertificateNumber());
            result.setWithVat(configurationService.isWithVat());
            result.setVatRate(configurationService.getVatRate());

            List<ReceiptData> receipts = new ArrayList<>();
            for (Receipt receipt : order.getReceipts()) {
                if (InvoiceUtils.isForCurrentInvoice(receipt.getCreationDate())) {
                    ReceiptData receiptData = new ReceiptData();
                    receiptData.setId(receipt.getId());
                    receiptData.setNumber(receipt.getNumber());
                    receiptData.setCreationDate(receipt.getCreationDate());

                    List<OrderDetailData> orderDetails = new ArrayList<>();
                    for (LineItem lineItem : receipt.getLineItems()) {
                        orderDetails.add(new OrderDetailData(lineItem));
                    }
                    receiptData.setOrderDetails(orderDetails);

                    receipts.add(receiptData);
                }
            }
            result.setReceipts(receipts);
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
