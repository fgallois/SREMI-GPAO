package fr.sremi.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.sremi.data.DocumentNumber;
import fr.sremi.services.ConfigurationService;
import fr.sremi.services.GeneratorService;

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
        return new GpaoConfiguration(generatorService.getCurrentReceiptNumber(),
                generatorService.getCurrentInvoiceNumber(), configurationService.getBlArchivePath(),
                configurationService.getExcelPath());
    }

    @RequestMapping(value = "/receiptNumber.json", method = RequestMethod.GET)
    public int currentReceiptNumber() {
        return generatorService.getNextReceiptNumber();
    }

    @RequestMapping(value = "/receiptNumber", method = RequestMethod.POST)
    public void updateReceiptNumber(@RequestBody DocumentNumber documentNumber) {
        generatorService.saveReceiptNumber(documentNumber.getDocumentNumber() - 1);
    }

    @RequestMapping(value = "/invoiceNumber.json", method = RequestMethod.GET)
    public int currentInvoiceNumber() {
        return generatorService.getNextInvoiceNumber();
    }

    @RequestMapping(value = "/invoiceNumber", method = RequestMethod.POST)
    public void updateInvoiceNumber(@RequestBody DocumentNumber invoiceNumber) {
        generatorService.saveInvoiceNumber(invoiceNumber.getDocumentNumber() - 1);
    }

    private class GpaoConfiguration {
        private final int receiptNumber;
        private final int invoiceNumber;
        private final String archivePath;
        private final String excelPath;

        private GpaoConfiguration(int receiptNumber, int invoiceNumber, String archivePath, String excelPath) {
            this.receiptNumber = receiptNumber;
            this.invoiceNumber = invoiceNumber;
            this.archivePath = archivePath;
            this.excelPath = excelPath;
        }

        public int getReceiptNumber() {
            return receiptNumber;
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
