package fr.sremi.services;

import javafx.beans.binding.DoubleExpression;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by fgallois on 9/1/15.
 */
@Component
public class ConfigurationService {

    @Value("${excel.file.path}")
    private String excelPath;

    @Value("${bl.archive.file.path}")
    private String blArchivePath;

    @Value("${invoice.archive.file.path}")
    private String invoiceArchivePath;

    @Value("${invoice.certificateNumber}")
    private String certificateNumber;

    @Value("${invoice.withVat}")
    private boolean withVat;

    @Value("${invoice.vatRate}")
    private Double vatRate;

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public String getBlArchivePath() {
        return blArchivePath;
    }

    public void setBlArchivePath(String blArchivePath) {
        this.blArchivePath = blArchivePath;
    }

    public String getInvoiceArchivePath() {
        return invoiceArchivePath;
    }

    public void setInvoiceArchivePath(String invoiceArchivePath) {
        this.invoiceArchivePath = invoiceArchivePath;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public boolean isWithVat() {
        return withVat;
    }

    public void setWithVat(boolean withVat) {
        this.withVat = withVat;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }

    //    private PropertiesConfiguration configuration;
//
//    @PostConstruct
//    private void init() {
//        try {
//            configuration = new PropertiesConfiguration("gpao-config.properties");
//        } catch (ConfigurationException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getExcelPath() {
//        return configuration.getString("excel.file.path");
//    }
//
//    public String getBlArchivePath() {
//        return configuration.getString("bl.archive.file.path");
//    }
//
//    public String getInvoiceArchivePath() {
//        return configuration.getString("invoice.archive.file.path");
//    }
//
//    public String getCertificateNumber() {
//        return configuration.getString("invoice.certificateNumber");
//    }
//
//    public void setCertificateNumber(String certificateNumber) {
//        configuration.setProperty("invoice.certificateNumber", certificateNumber);
//        save();
//    }
//
//    public Boolean getWithVat() {
//        return configuration.getBoolean("invoice.withVat");
//    }
//
//    public void setWithVat(Boolean withVat) {
//        configuration.setProperty("invoice.withVat", withVat);
//        save();
//    }
//
//    public Double getVatRate() {
//        return configuration.getDouble("invoice.vatRate");
//    }
//
//    public void setVatRate(Double vatRate) {
//        configuration.setProperty("invoice.vatRate", vatRate);
//        save();
//    }
//
//    private void save() {
//        try {
//            configuration.save();
//        } catch (ConfigurationException e) {
//            e.printStackTrace();
//        }
//    }
}
