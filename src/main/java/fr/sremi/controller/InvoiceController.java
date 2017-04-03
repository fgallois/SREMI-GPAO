package fr.sremi.controller;

import fr.sremi.data.invoice.InvoiceData;
import fr.sremi.exception.PdfException;
import fr.sremi.services.GeneratorService;
import fr.sremi.services.InvoiceService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by fgallois on 11/8/15.
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Resource
    private InvoiceService invoiceService;

    @Resource
    private GeneratorService generatorService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createInvoice(@RequestBody InvoiceData invoiceData) {
        try {
            String filename = invoiceService.createInvoice(invoiceData.getReference());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{filename}")
                    .buildAndExpand(filename).toUri());
            httpHeaders.set("invoiceNumber", String.valueOf(generatorService.getNextInvoiceNumber()));
            return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(null);
        } catch (PdfException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/{filename}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> readBonLivraison(@PathVariable String filename) throws IOException {
        org.springframework.core.io.Resource pdfFile = invoiceService.readInvoice(filename + ".pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline;filename=" + filename + ".pdf");

        return ResponseEntity.ok().headers(headers).contentLength(pdfFile.contentLength())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(pdfFile.getInputStream()));
    }

}
