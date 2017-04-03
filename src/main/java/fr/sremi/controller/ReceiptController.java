package fr.sremi.controller;

import fr.sremi.data.ReceiptData;
import fr.sremi.data.invoice.InvoiceData;
import fr.sremi.exception.PdfException;
import fr.sremi.services.OrderService;
import fr.sremi.services.ReceiptService;
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
 * Created by fgallois on 9/7/15.
 */
@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    @Resource
    private ReceiptService receiptService;

    @Resource
    private OrderService orderService;

    @PostMapping
    public ResponseEntity createBonLivraison(@RequestBody ReceiptData receiptData) {
        try {
            String filename = receiptService.createBL(receiptData);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{filename}")
                    .buildAndExpand(filename).toUri());
            httpHeaders.set("receiptNumber", receiptService.getNextReceiptNumber());
            return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(null);
        } catch (PdfException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/{filename}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> readBonLivraison(@PathVariable String filename) throws IOException {
        org.springframework.core.io.Resource pdfFile = receiptService.readBL(filename + ".pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline;filename=" + filename + ".pdf");

        return ResponseEntity.ok().headers(headers).contentLength(pdfFile.contentLength())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(pdfFile.getInputStream()));
    }

    @DeleteMapping(value = "/{commandeRef}/{receiptRef}")
    public InvoiceData getOpenOrderDetails(@PathVariable String commandeRef, @PathVariable String receiptRef) {
        return orderService.removeReceiptFromOrder(commandeRef, receiptRef);
    }
}
