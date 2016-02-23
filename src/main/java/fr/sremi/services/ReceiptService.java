package fr.sremi.services;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import fr.sremi.data.ReceiptData;
import fr.sremi.exception.PdfException;
import fr.sremi.services.pdf.PdfReceiptCreator;

/**
 * Created by fgallois on 9/7/15.
 */
@Component
public class ReceiptService {

    @Resource
    private PdfReceiptCreator pdfReceiptCreator;

    @Resource
    private ConfigurationService configurationService;

    @Resource
    private GeneratorService generatorService;

    @Resource
    OrderService orderService;

    public String createBL(ReceiptData receiptData) {
        int receiptNumber = generatorService.getNextReceiptNumber();
        String filename = "BL-" + receiptNumber + ".pdf";
        try {
            File archiveFile = new File(configurationService.getBlArchivePath() + filename);

            pdfReceiptCreator.createPdf(String.valueOf(receiptNumber), receiptData.getOrderRef(),
                    receiptData.getLines(), archiveFile);
            orderService.saveOrderReceipt(receiptData.getOrderRef(), receiptData.getLines(), receiptNumber);
            generatorService.saveReceiptNumber(receiptNumber);
        } catch (PdfException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public org.springframework.core.io.Resource readBL(String filename) {
        org.springframework.core.io.Resource resource = null;

        String uri = configurationService.getBlArchivePath() + filename;
        File file = new File(uri);
        if (file.exists()) {
            resource = new FileSystemResource(uri);
        }
        return resource;
    }
}
