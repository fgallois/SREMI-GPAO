package fr.sremi.services;

import fr.sremi.exception.PdfException;
import fr.sremi.services.pdf.PdfInvoiceCreator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by fgallois on 12/8/15.
 */
@Component
public class InvoiceService {

    @Resource
    private PdfInvoiceCreator pdfInvoiceCreator;

    @Resource
    private GeneratorService generatorService;

    @Resource
    private ConfigurationService configurationService;

    @Resource
    private OrderService orderService;

    public String createInvoice(String orderRef) throws PdfException {
        int invoiceNumber = generatorService.getNextInvoiceNumber();

        String filename = pdfInvoiceCreator.createPdf(String.valueOf(invoiceNumber), orderService.getInvoiceData(orderRef));
        generatorService.saveInvoiceNumber(invoiceNumber);
        orderService.updateInvoiceDate(orderRef);

        return filename;
    }

    public org.springframework.core.io.Resource readInvoice(String filename) {
        org.springframework.core.io.Resource resource = null;

        String uri = configurationService.getInvoiceArchivePath() + filename;
        File file = new File(uri);
        if (file.exists()) {
            resource = new FileSystemResource(uri);
        }
        return resource;
    }
}
