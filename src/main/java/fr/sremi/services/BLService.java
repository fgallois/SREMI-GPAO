package fr.sremi.services;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import fr.sremi.data.BLData;
import fr.sremi.exception.PdfException;

/**
 * Created by fgallois on 9/7/15.
 */
@Component
public class BLService {

    @Resource
    private PdfService pdfService;

    @Resource
    private ConfigurationService configurationService;

    public String createBL(BLData blData) {
        String filename = "bl-" + configurationService.getInvoiceNumber() + ".pdf";
        // FileInputStream result = null;
        try {
            File archiveFile = new File(configurationService.getArchivePath() + filename);
            // File archiveFile = new File(configurationService.getArchivePath() + "bl.pdf");

            pdfService.generatePdf(String.valueOf(configurationService.getInvoiceNumber()), blData.getOrderRef(),
                    blData.getLines(), archiveFile);
            // outputstream.toByteArray()
            // result = new FileInputStream(archiveFile);
        } catch (PdfException e) {
            e.printStackTrace();
        }
        return filename;
    }
}
