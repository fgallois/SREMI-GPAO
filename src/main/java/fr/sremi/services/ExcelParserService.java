package fr.sremi.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import fr.sremi.exception.ExcelException;
import fr.sremi.vo.Command;
import fr.sremi.vo.ItemCommand;

public class ExcelParserService {

    public List<Command> getCommandsFromExcelFile(File file) throws ExcelException {
        List<Command> result = new ArrayList<Command>();
        Map<String, Command> commands = new HashMap<String, Command>();

        try {
            InputStream inp = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));

            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                // Saute la 1ere ligne qui contient les titres.
                if (row.getRowNum() > 0) {
                    String refCommand = row.getCell(1).getStringCellValue();
                    Command command = commands.get(refCommand); // Reference command
                    if (command == null) {
                        command = new Command(refCommand, row.getCell(0).getStringCellValue());
                        commands.put(refCommand, command);
                        result.add(command);
                    }
                    ItemCommand itemCommand = new ItemCommand();
                    for (Cell cell : row) {
                        switch (cell.getColumnIndex()) {
                        case 2: // Ligne
                            itemCommand.setLine((int) cell.getNumericCellValue());
                            break;
                        case 3: // Reference article
                            itemCommand.getItem().setReference(cell.getStringCellValue());
                            break;
                        case 4: // Description article
                            itemCommand.getItem().setDescription(cell.getStringCellValue());
                            break;
                        case 5: // Emplacement
                            itemCommand.getItem().setEmplacement(cell.getStringCellValue());
                            break;
                        case 6: // Quantite
                            itemCommand.setQuantity((int) cell.getNumericCellValue());
                            break;
                        case 7: // Date d'echeance
                            itemCommand.setDueDate(cell.getDateCellValue());
                            break;
                        case 8: // Revision
                            itemCommand.setVersion(cell.getStringCellValue());
                        }
                    }
                    command.addItem(itemCommand);
                }
            }

        } catch (FileNotFoundException e) {
            throw new ExcelException("Can't find the Excel file: " + file.getName(), e);
        } catch (IOException e) {
            throw new ExcelException("Can't read the Excel file: " + file.getName(), e);
        }

        return result;
    }
}
