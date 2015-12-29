package fr.sremi.services;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.stereotype.Component;

/**
 * Created by fgallois on 9/1/15.
 */
@Component
public class ConfigurationService {

    private PropertiesConfiguration configuration;

    @PostConstruct
    private void init() {
        try {
            configuration = new PropertiesConfiguration("gpao-config.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getExcelPath() {
        return configuration.getString("excel.file.path");
    }

    public String getBlArchivePath() {
        return configuration.getString("bl.archive.file.path");
    }

    public String getInvoiceArchivePath() {
        return configuration.getString("invoice.archive.file.path");
    }

    public String getCertificateNumber() {
        return configuration.getString("invoice.certificateNumber");
    }

    public void setCertificateNumber(String certificateNumber) {
        configuration.setProperty("invoice.certificateNumber", certificateNumber);
        save();
    }

    public Boolean getWithVat() {
        return configuration.getBoolean("invoice.withVat");
    }

    public void setWithVat(Boolean withVat) {
        configuration.setProperty("invoice.withVat", withVat);
        save();
    }

    public Double getVatRate() {
        return configuration.getDouble("invoice.vatRate");
    }

    public void setVatRate(Double vatRate) {
        configuration.setProperty("invoice.vatRate", vatRate);
        save();
    }

    private void save() {
        try {
            configuration.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
