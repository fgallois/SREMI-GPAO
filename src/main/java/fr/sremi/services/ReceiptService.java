package fr.sremi.services;

import java.io.File;

import javax.annotation.Resource;

import fr.sremi.data.ReceiptData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import fr.sremi.exception.PdfException;

/**
 * Created by fgallois on 9/7/15.
 */
@Component
public class ReceiptService {

    @Resource
    private PdfService pdfService;

    @Resource
    private ConfigurationService configurationService;

    @Resource
    private GeneratorService generatorService;

    public String createBL(ReceiptData receiptData) {
        int invoiceNumber = generatorService.getNewReceiptNumber();
        String filename = "BL-" + invoiceNumber + ".pdf";
        try {
            File archiveFile = new File(configurationService.getArchivePath() + filename);

            pdfService.generatePdf(String.valueOf(invoiceNumber), receiptData.getOrderRef(),
                    receiptData.getLines(), archiveFile);
        } catch (PdfException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public org.springframework.core.io.Resource readBL(String filename) {
        org.springframework.core.io.Resource resource = null;

        String uri = configurationService.getArchivePath() + filename;
        File file = new File(uri);
        if (file.exists()) {
            resource = new FileSystemResource(uri);
        }
        return resource;
    }
}