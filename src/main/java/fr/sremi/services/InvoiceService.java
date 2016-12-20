package fr.sremi.services;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import fr.sremi.exception.PdfException;
import fr.sremi.services.pdf.PdfInvoiceCreator;

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

    public String createInvoice(String orderRef) {
        int invoiceNumber = generatorService.getNextInvoiceNumber();
        String filename = "FACTURE-" + invoiceNumber + ".pdf";
        try {
            File archiveFile = new File(configurationService.getInvoiceArchivePath() + filename);

            pdfInvoiceCreator.createPdf(String.valueOf(invoiceNumber), orderService.getInvoiceData(orderRef),
                    archiveFile);
            generatorService.saveInvoiceNumber(invoiceNumber);
        } catch (PdfException e) {
            e.printStackTrace();
        }
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
