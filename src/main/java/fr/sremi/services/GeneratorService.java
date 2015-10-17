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

    @Resource
    private GeneratorRepository generatorRepository;

    public int getNewReceiptNumber() {
        int receiptNumber = 1;
        Generator generator = generatorRepository.findByType(RECEIPT_TYPE);
        if (generator == null) {
            generator = new Generator(receiptNumber, RECEIPT_TYPE);
        } else {
            receiptNumber = generator.getNumber() + 1;
            generator.setNumber(receiptNumber);
        }
        generatorRepository.save(generator);
        return receiptNumber;
    }

    public int getCurrentReceiptNumber() {
        int receiptNumber = -1;
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
}
