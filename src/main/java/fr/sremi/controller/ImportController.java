package fr.sremi.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.sremi.dao.PartRepository;
import fr.sremi.model.Part;
import fr.sremi.model.Price;

/**
 * Created by fgallois on 9/25/15.
 */
@RestController
@RequestMapping(value = "/import")
public class ImportController {

    @Resource
    private PartRepository partRepository;

    @RequestMapping(method = RequestMethod.GET)
    public void importPartsFromCsvFile() {
        System.out.println("***** IMPORT PARTS *****");

        Reader in = null;
        try {
            StringBuilder duplicates = new StringBuilder(200);
            in = new FileReader("/Users/fgallois/MyDev/SERAC.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
            for (CSVRecord record : records) {
                String reference = record.get("Reference");
                String type = record.get("Type");
                String designation = record.get("Designation");
                String prix1 = record.get("Prix 1");
                String q1 = record.get("Q1");
                String date = record.get("Date 1");
                String prixMatiere = record.get("Prix Mat");
                String prixParPiece = record.get("P/1piece");
                String commentaire = record.get("Commentaires");

                Part part = new Part(getReference(reference), getModification(reference), isPlanTableau(type),
                        designation);
                part.setComment(commentaire);
                if (StringUtils.contains(commentaire, "plan d")) {
                    part.setRetired(Boolean.TRUE);
                }
                List<Price> prices = new ArrayList<>();
                if (StringUtils.isNotEmpty(prixParPiece)) {
                    Price price = new Price(Double.valueOf(prixParPiece), 1);
                    prices.add(price);
                }
                if (StringUtils.isNotEmpty(prix1) && StringUtils.isNotEmpty(q1)) {
                    Price price = new Price(Double.valueOf(prix1), Integer.valueOf(q1));
                    Date newDate = formatDate(date);
                    if (newDate != null) {
                        price.setDate(newDate);
                    }
                    prices.add(price);
                }
                part.setPrices(prices);
                try {
                    partRepository.save(part);
                } catch (Exception e) {
                    duplicates.append(part.getReference()).append(" , ");
                }

                // System.out.println("Ref = " + reference + "    Type = " + type);
            }
            System.out.println("Duplicates = " + duplicates.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean isPlanTableau(String type) {
        Boolean result = Boolean.FALSE;
        if (StringUtils.isNotEmpty(type)) {
            if ("pt".equals(type)) {
                result = Boolean.TRUE;
            }
        }
        return result;
    }

    private String getReference(final String reference) {
        String result = StringUtils.substringBefore(reference, "/");
        result = StringUtils.substringBefore(result, " ");
        result = StringUtils.remove(result, ".");
        return result;
    }

    private String getModification(String reference) {
        return StringUtils.substringAfter(reference, "/");
    }

    private Date formatDate(String date) {
        Date result = null;
        if (StringUtils.isNoneEmpty(date)) {
            DateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
            try {
                result = format.parse(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
