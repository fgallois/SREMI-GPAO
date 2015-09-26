package fr.sremi.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import fr.sremi.data.OrderData;
import org.springframework.stereotype.Component;

import fr.sremi.data.OrderDetailData;
import fr.sremi.exception.ExcelException;
import fr.sremi.vo.Command;
import fr.sremi.vo.ItemCommand;

/**
 * Created by fgallois on 9/6/15.
 */
@Component
public class OrderService {

    @Resource
    ConfigurationService configurationService;

    public List<OrderData> getAvailableOrders() {
        List<OrderData> result = new ArrayList<>();

        ExcelParserService excelParser = new ExcelParserService();

        File excelFile = new File(configurationService.getExcelPath());
        try {
            List<Command> commands = excelParser.getCommandsFromExcelFile(excelFile);
            for (Command command : commands) {
                result.add(new OrderData(1, command.getReference()));
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
}
