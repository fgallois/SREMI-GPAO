package fr.sremi.controller;

import java.io.IOException;

import javax.annotation.Resource;

import fr.sremi.data.ReceiptData;
import fr.sremi.services.GeneratorService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.sremi.services.ReceiptService;

/**
 * Created by fgallois on 9/7/15.
 */
@RestController
@RequestMapping("/bonLivraison")
public class ReceiptController {

    @Resource
    private ReceiptService receiptService;

    @Resource
    private GeneratorService generatorService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createBonLivraison(@RequestBody ReceiptData receiptData) {

        String filename = receiptService.createBL(receiptData);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{filename}")
                .buildAndExpand(filename).toUri());
        httpHeaders.set("receiptNumber", String.valueOf(generatorService.getNextReceiptNumber()));
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{filename}", method = RequestMethod.GET, produces = "application/pdf")
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
}
