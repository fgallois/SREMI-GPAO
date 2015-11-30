package fr.sremi.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import fr.sremi.dao.OrderRepository;
import fr.sremi.dao.PartRepository;
import fr.sremi.data.OrderData;
import fr.sremi.data.OrderDetailData;
import fr.sremi.exception.ExcelException;
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
    PartRepository partRepository;

    public void importOrders() {
        ExcelParserService excelParser = new ExcelParserService();

        File excelFile = new File(configurationService.getExcelPath());
        try {
            List<Command> commands = excelParser.getCommandsFromExcelFile(excelFile);
            for (Command command : commands) {
                if (orderRepository.findByReference(command.getReference()) == null) {
                    Order order = new Order(command.getReference());
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

        List<Order> orders = orderRepository.findByOpenTrue();
        for (Order order : orders) {
            result.add(new OrderData(order.getId(), order.getReference()));
        }
        return result;
    }

    public List<OrderDetailData> getOpenOrderDetails(final String orderRef) {
        List<OrderDetailData> result = new ArrayList<>();

        Order order = orderRepository.findByReference(orderRef);

        if (order != null) {
            for (LineItem lineItem : order.getLineItems()) {
                OrderDetailData orderData = new OrderDetailData();
                orderData.setLine(lineItem.getLine());
                orderData.setReference(lineItem.getPart().getReference());
                orderData.setDescription(lineItem.getPart().getDescription());
                orderData.setQuantity(lineItem.getQuantity());
                orderData.setDueDate(lineItem.getDueDate());
                orderData.setUnitPriceHT(lineItem.getUnitPrice());
                result.add(orderData);
            }
        }
        return result;
    }
}
