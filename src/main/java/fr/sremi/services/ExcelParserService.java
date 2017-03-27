package fr.sremi.services;

import fr.sremi.exception.ExcelException;
import fr.sremi.vo.Command;
import fr.sremi.vo.ItemCommand;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ExcelParserService {

    public List<Command> getCommandsFromExcelFile(File file) throws ExcelException {
        try {
            InputStream inp = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));

            return StreamSupport.stream(wb.getSheetAt(0).spliterator(), false)
                    .skip(1)
                    .collect(
                            Collectors.groupingBy(
                                    row -> row.getCell(1).getStringCellValue(),
                                    Collectors.mapping(row -> {
                                        ItemCommand itemCommand = new ItemCommand();
                                        itemCommand.setLine(row.getCell(2) != null ? (int) row.getCell(2).getNumericCellValue() : null);
                                        itemCommand.getItem().setReference(row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null);
                                        itemCommand.getItem().setDescription(row.getCell(7) != null ? row.getCell(4).getStringCellValue() : null);
                                        itemCommand.getItem().setEmplacement(row.getCell(5) != null ? row.getCell(5).getStringCellValue() : null);
                                        itemCommand.setQuantity(row.getCell(6) != null ? (int) row.getCell(6).getNumericCellValue() : null);
                                        itemCommand.setDueDate(row.getCell(7) != null ? row.getCell(7).getDateCellValue() : null);
                                        itemCommand.setVersion(row.getCell(8) != null ? row.getCell(8).getStringCellValue() : null);
                                        return itemCommand;
                                    }, Collectors.toList())))
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
