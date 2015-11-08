package fr.sremi.controller;

import javax.annotation.Resource;

import fr.sremi.data.ReceiptNumber;
import fr.sremi.services.GeneratorService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.sremi.services.ConfigurationService;

/**
 * Created by fgallois on 8/23/15.
 */
@RestController
public class ConfigurationController {

    @Resource
    private ConfigurationService configurationService;

    @Resource
    private GeneratorService generatorService;

    @RequestMapping(value = "/configuration.json", method = RequestMethod.GET)
    public GpaoConfiguration gpaoConfiguration() {
        return new GpaoConfiguration(generatorService.getCurrentReceiptNumber(), configurationService.getArchivePath(),
                configurationService.getExcelPath());
    }

    @RequestMapping(value = "/receiptNumber.json", method = RequestMethod.GET)
    public int currentReceiptNumber() {
        return generatorService.getNextReceiptNumber();
    }

    @RequestMapping(value = "/receiptNumber", method = RequestMethod.POST)
    public void updateReceiptNumber(@RequestBody ReceiptNumber receiptNumber) {
        generatorService.saveReceiptNumber(receiptNumber.getReceiptNumber() - 1);
    }

    private class GpaoConfiguration {
        private final int receiptNumber;
        private final String archivePath;
        private final String excelPath;

        private GpaoConfiguration(int receiptNumber, String archivePath, String excelPath) {
            this.receiptNumber = receiptNumber;
            this.archivePath = archivePath;
            this.excelPath = excelPath;
        }

        public int getReceiptNumber() {
            return receiptNumber;
        }

        public String getArchivePath() {
            return archivePath;
        }

        public String getExcelPath() {
            return excelPath;
        }
    }
}
