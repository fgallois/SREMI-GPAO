package fr.sremi.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import fr.sremi.util.InvoiceUtils;
import org.springframework.stereotype.Component;

import fr.sremi.dao.BuyerRepository;
import fr.sremi.dao.OrderLineItemRepository;
import fr.sremi.dao.OrderRepository;
import fr.sremi.dao.PartRepository;
import fr.sremi.data.OrderData;
import fr.sremi.data.OrderDetailData;
import fr.sremi.data.invoice.InvoiceData;
import fr.sremi.data.invoice.ReceiptData;
import fr.sremi.exception.ExcelException;
import fr.sremi.model.Buyer;
import fr.sremi.model.LineItem;
import fr.sremi.model.Order;
import fr.sremi.model.Part;
import fr.sremi.model.Receipt;
import fr.sremi.vo.Command;
import fr.sremi.vo.ItemCommand;

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

    public void importOrders() {
        ExcelParserService excelParser = new ExcelParserService();

        File excelFile = new File(configurationService.getExcelPath());
        try {
            List<Command> commands = excelParser.getCommandsFromExcelFile(excelFile);
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
        } catch (ExcelException e) {
            e.printStackTrace();
        }
    }




    public List<OrderData> getAvailableOrders() {
        List<OrderData> result = new ArrayList<>();

        ExcelParserService excelParser = new ExcelParserService();

        File excelFile = new File(configurationService.getExcelPath());
        try {
            List<Command> commands = excelParser.getCommandsFromExcelFile(excelFile);
            for (Command command : commands) {
                result.add(new OrderData(null, command.getReference()));
            }
        } catch (ExcelException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<OrderDetailData> getOrderDetails(String orderRef) {
        List<OrderDetailData> result = new ArrayList<>();

        ExcelParserService excelParser = new ExcelParserService();

        File excelFile = new File(configurationService.getExcelPath());
        try {
            List<Command> commands = excelParser.getCommandsFromExcelFile(excelFile);
            for (Command command : commands) {
                if (orderRef.equalsIgnoreCase(command.getReference())) {
                    for (ItemCommand itemCommand : command.getItems()) {
                        OrderDetailData detail = new OrderDetailData();
                        detail.setLine(itemCommand.getLine());
                        detail.setReference(itemCommand.getItem().getReference());
                        detail.setDescription(itemCommand.getItem().getDescription());
                        detail.setQuantity(itemCommand.getQuantity());
                        detail.setDueDate(itemCommand.getDueDate());
                        result.add(detail);
                    }
                }
            }
        } catch (ExcelException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void saveOrderReceipt(String orderRef, int invoiceNumber) {
        Order order = orderRepository.findByReference(orderRef);
        if (order != null) {
            Receipt receipt = new Receipt(invoiceNumber);

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

            List<OrderDetailData> orderDetails = new ArrayList<>();
            for (LineItem lineItem : order.getLineItems()) {
                OrderDetailData orderData = new OrderDetailData();
                orderData.setId(lineItem.getId());
                orderData.setLine(lineItem.getLine());
                orderData.setReference(lineItem.getPart().getReference());
                orderData.setDescription(lineItem.getPart().getDescription());
                orderData.setQuantity(lineItem.getQuantity());
                orderData.setDueDate(lineItem.getDueDate());
                if (lineItem.getUnitPrice() == null) {
                    orderData.setUnitPriceHT(Double.valueOf(0));
                } else {
                    orderData.setUnitPriceHT(lineItem.getUnitPrice());
                }
                orderDetails.add(orderData);
            }
            result.setOrderDetails(orderDetails);

            List<ReceiptData> receipts = new ArrayList<>();
            for (Receipt receipt : order.getReceipts()) {
                ReceiptData receiptData = new ReceiptData();
                receiptData.setId(receipt.getId());
                receiptData.setNumber(receipt.getNumber());
                receiptData.setCreationDate(receipt.getCreationDate());
                receipts.add(receiptData);
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
        order.setInvoiceDate(InvoiceUtils.currentInvoiceDate());
        orderRepository.save(order);
    }
}
