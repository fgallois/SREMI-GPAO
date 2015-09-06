package fr.sremi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by fgallois on 9/1/15.
 */
@Component
public class ConfigurationService {

    @Value("${invoice.number}")
    private int invoiceNumber;

    @Value("${excel.file.path}")
    private String excelPath;

    @Value("${archive.file.path}")
    private String archivePath;

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public String getArchivePath() {
        return archivePath;
    }
}
