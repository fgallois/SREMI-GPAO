package fr.sremi.services;

import fr.sremi.data.ReceiptData;
import fr.sremi.exception.PdfException;
import fr.sremi.services.pdf.PdfReceiptCreator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by fgallois on 9/7/15.
 */
@Component
public class ReceiptService {

    @Resource
    private OrderService orderService;
    @Resource
    private PdfReceiptCreator pdfReceiptCreator;
    @Resource
    private ConfigurationService configurationService;
    @Resource
    private GeneratorService generatorService;

    public String createBL(ReceiptData receiptData) throws PdfException {
        int receiptNumber = generatorService.getNextReceiptNumber();
        String filename = pdfReceiptCreator.createPdf(String.valueOf(receiptNumber), receiptData);
        orderService.saveOrderReceipt(receiptData.getOrderRef(), receiptData.getLines(), receiptNumber);
        generatorService.saveReceiptNumber(receiptNumber);
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

    public String getNextReceiptNumber() {
        return String.valueOf(generatorService.getNextReceiptNumber());
    }
}
