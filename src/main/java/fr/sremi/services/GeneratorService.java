package fr.sremi.services;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import fr.sremi.dao.GeneratorRepository;
import fr.sremi.model.Generator;

/**
 * Created by fgallois on 9/12/15.
 */
@Component
@Transactional
public class GeneratorService {

    private static String RECEIPT_TYPE = "RECEIPT";
    private static String INVOICE_TYPE = "INVOICE";

    @Resource
    private GeneratorRepository generatorRepository;

    public int getNextReceiptNumber() {
        int receiptNumber = 0;
        Generator generator = generatorRepository.findByType(RECEIPT_TYPE);
        if (generator != null) {
            receiptNumber = generator.getNumber() + 1;
        }
        return receiptNumber;
    }

    public int getCurrentReceiptNumber() {
        int receiptNumber = 0;
        Generator generator = generatorRepository.findByType(RECEIPT_TYPE);
        if (generator != null) {
            receiptNumber = generator.getNumber();
        }
        return receiptNumber;
    }

    public void saveReceiptNumber(int receiptNumber) {
        Generator generator = generatorRepository.findByType(RECEIPT_TYPE);
        if (generator == null) {
            generator = new Generator(receiptNumber, RECEIPT_TYPE);
        } else {
            generator.setNumber(receiptNumber);
        }
        generatorRepository.save(generator);
    }

    public int getNextInvoiceNumber() {
        int invoiceNumber = 0;
        Generator generator = generatorRepository.findByType(INVOICE_TYPE);
        if (generator != null) {
            invoiceNumber = generator.getNumber() + 1;
        }
        return invoiceNumber;
    }

    public int getCurrentInvoiceNumber() {
        int invoiceNumber = 0;
        Generator generator = generatorRepository.findByType(INVOICE_TYPE);
        if (generator != null) {
            invoiceNumber = generator.getNumber();
        }
        return invoiceNumber;
    }

    public void saveInvoiceNumber(int invoiceNumber) {
        Generator generator = generatorRepository.findByType(INVOICE_TYPE);
        if (generator == null) {
            generator = new Generator(invoiceNumber, INVOICE_TYPE);
        } else {
            generator.setNumber(invoiceNumber);
        }
        generatorRepository.save(generator);
    }
}
