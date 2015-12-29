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
                configurationService.getInvoiceArchivePath(), configurationService.getExcelPath(),
                configurationService.getCertificateNumber(), configurationService.getWithVat(),
                configurationService.getVatRate());
    }

    @RequestMapping(value = "/receiptNumber.json", method = RequestMethod.GET)
    public int currentReceiptNumber() {
        return generatorService.getNextReceiptNumber();
    }

    @RequestMapping(value = "/receiptNumber", method = RequestMethod.POST)
    public void updateReceiptNumber(@RequestBody DocumentNumber documentNumber) {
        generatorService.saveReceiptNumber(Integer.valueOf(documentNumber.getDocumentNumber()) - 1);
    }

    @RequestMapping(value = "/invoiceNumber.json", method = RequestMethod.GET)
    public int currentInvoiceNumber() {
        return generatorService.getNextInvoiceNumber();
    }

    @RequestMapping(value = "/invoiceNumber", method = RequestMethod.POST)
    public void updateInvoiceNumber(@RequestBody DocumentNumber invoiceNumber) {
        generatorService.saveInvoiceNumber(Integer.valueOf(invoiceNumber.getDocumentNumber()) - 1);
    }

    @RequestMapping(value = "/certificateNumber", method = RequestMethod.POST)
    public void updateCertificateNumber(@RequestBody DocumentNumber certificateNumber) {
        System.out.println("Certificate = " + certificateNumber.getDocumentNumber());
        configurationService.setCertificateNumber(certificateNumber.getDocumentNumber());
    }

    @RequestMapping(value = "/vatRate", method = RequestMethod.POST)
    public void updateVatRate(@RequestBody DocumentNumber certificateNumber) {
        System.out.println("Certificate = " + certificateNumber.getDocumentNumber());
        configurationService.setVatRate(Double.valueOf(certificateNumber.getDocumentNumber()));
    }

    private class GpaoConfiguration {
        private final int receiptNumber;
        private final int invoiceNumber;
        private final String receiptArchivePath;
        private final String invoiceArchivePath;
        private final String excelPath;
        private final String certificateNumber;
        private final Boolean withVat;
        private final Double vatRate;

        private GpaoConfiguration(int receiptNumber, int invoiceNumber, String receiptArchivePath,
                String invoiceArchivePath, String excelPath, String certificateNumber, Boolean withVat, Double vatRate) {
            this.receiptNumber = receiptNumber;
            this.invoiceNumber = invoiceNumber;
            this.receiptArchivePath = receiptArchivePath;
            this.invoiceArchivePath = invoiceArchivePath;
            this.excelPath = excelPath;
            this.certificateNumber = certificateNumber;
            this.withVat = withVat;
            this.vatRate = vatRate;

        }

        public int getReceiptNumber() {
            return receiptNumber;
        }

        public int getInvoiceNumber() {
            return invoiceNumber;
        }

        public String getReceiptArchivePath() {
            return receiptArchivePath;
        }

        public String getInvoiceArchivePath() {
            return invoiceArchivePath;
        }

        public String getExcelPath() {
            return excelPath;
        }

        public String getCertificateNumber() {
            return certificateNumber;
        }

        public Boolean getWithVat() {
            return withVat;
        }

        public Double getVatRate() {
            return vatRate;
        }
    }
}
