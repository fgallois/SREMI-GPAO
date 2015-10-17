package fr.sremi.controller;

import javax.annotation.Resource;

import fr.sremi.data.InvoiceNumber;
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

    @RequestMapping(value = "/invoiceNumber.json", method = RequestMethod.GET)
    public int currentInvoiceNumber() {
        return generatorService.getCurrentReceiptNumber();
    }

    @RequestMapping(value = "/invoiceNumber", method = RequestMethod.POST)
    public void updateInvoiceNumber(@RequestBody InvoiceNumber invoiceNumber) {
        generatorService.saveReceiptNumber(invoiceNumber.getInvoiceNumber());
    }

    private class GpaoConfiguration {
        private final int invoiceNumber;
        private final String archivePath;
        private final String excelPath;

        private GpaoConfiguration(int invoiceNumber, String archivePath, String excelPath) {
            this.invoiceNumber = invoiceNumber;
            this.archivePath = archivePath;
            this.excelPath = excelPath;
        }

        public int getInvoiceNumber() {
            return invoiceNumber;
        }

        public String getArchivePath() {
            return archivePath;
        }

        public String getExcelPath() {
            return excelPath;
        }
    }
}
