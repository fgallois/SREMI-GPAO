package fr.sremi.services;

import fr.sremi.exception.ExcelException;
import fr.sremi.model.Client;
import fr.sremi.vo.Command;
import fr.sremi.vo.ItemCommandBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ExcelParserService {

    @Resource
    ConfigurationService configurationService;


    public List<Command> getClientCommands(Client client) throws ExcelException {
        File excelFile = new File(configurationService.getExcelPath() + client.getOrderFilename());
        return getCommandsFromExcelFile(excelFile);
    }

    private List<Command> getCommandsFromExcelFile(File file) throws ExcelException {
        try (InputStream inp = new FileInputStream(file)) {

            HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));

            return StreamSupport.stream(wb.getSheetAt(0).spliterator(), false)
                    .skip(1)
                    .collect(
                            Collectors.groupingBy(
                                    row -> row.getCell(1).getStringCellValue(),
                                    Collectors.mapping(row -> new ItemCommandBuilder()
                                                .setLine(row.getCell(2))
                                                .setReference(row.getCell(3))
                                                .setDescription(row.getCell(4))
                                                .setEmplacement(row.getCell(5))
                                                .setQuantity(row.getCell(6))
                                                .setDueDate(row.getCell(7))
                                                .setVersion(row.getCell(8))
                                                .build()
                                    , Collectors.toList())))
                    .entrySet().stream()
                    .map(e -> new Command(e.getKey(), "SREMI", e.getValue()))
                    .collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            throw new ExcelException("Can't find the Excel file: " + file.getName(), e);
        } catch (IOException e) {
            throw new ExcelException("Can't read the Excel file: " + file.getName(), e);
        }
    }
}
