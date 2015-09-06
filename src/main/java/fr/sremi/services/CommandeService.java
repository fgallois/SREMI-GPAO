package fr.sremi.services;

import fr.sremi.data.CommandeData;
import fr.sremi.exception.ExcelException;
import fr.sremi.model.Command;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgallois on 9/6/15.
 */
@Component
public class CommandeService {

    @Resource
    ConfigurationService configurationService;

    public List<CommandeData> getAvailableCommandes() {
        List<CommandeData> result = new ArrayList<>();

        ExcelParserService excelParser = new ExcelParserService();

        File excelFile = new File(configurationService.getExcelPath());
        try {
            List<Command> commands = excelParser.getCommandsFromExcelFile(excelFile);
            for (Command command: commands) {
                result.add(new CommandeData(1, command.getReference()));
            }
        } catch (ExcelException e) {
            e.printStackTrace();
        }
        return result;
    }
}
