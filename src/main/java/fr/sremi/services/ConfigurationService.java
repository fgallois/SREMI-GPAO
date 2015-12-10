package fr.sremi.services;

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

    public String getExcelPath() {
        return excelPath;
    }

    public String getBlArchivePath() {
        return blArchivePath;
    }

    public String getInvoiceArchivePath() {
        return invoiceArchivePath;
    }
}
