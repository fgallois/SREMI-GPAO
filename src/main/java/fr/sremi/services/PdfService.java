package fr.sremi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.sremi.data.OrderDetailData;
import fr.sremi.exception.PdfException;

@Component
public class PdfService {

    public void generatePdf(String invoiceNumber, String referenceCommand, List<OrderDetailData> commands, File file)
            throws PdfException {
        Document document = new Document(PageSize.A4);

        try {
            FileOutputStream fileout = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, fileout);
            writer.setPageEvent(new PdfFooterEvent());
            writer.setPageEvent(new PdfHeaderEvent());

            document.open();

            // Page 1: Exemplaire client
            document.add(createInformations());
            document.add(createInfoCommand(referenceCommand, invoiceNumber));
            document.add(createCommandTable(commands));
            document.newPage();

            // Page 2: Exemplaire SREMI
            document.add(createInformations());
            document.add(createInfoCommand(referenceCommand, invoiceNumber));
            document.add(createCommandTable(commands));
            document.newPage();

        } catch (FileNotFoundException e) {
            throw new PdfException("Impossible de trouver le fichier", e);
        } catch (DocumentException e) {
            throw new PdfException("Erreur de creation du fichier Pdf", e);
        } finally {
            document.close();
        }
    }

    private Element createInformations() {
        float[] colsWidth = { 58f, 42f };
        PdfPTable table = new PdfPTable(colsWidth);
        table.setWidthPercentage(100);

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase("SREMI", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 28)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("SARL au capital de 15500 Euros", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("Touche Fougère", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("72320 Saint Maixent", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("N° Siret: 4397548100015", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("APE: 3320C", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("N° Intracommunautaire: FR 26 439 754 581", FontFactory.getFont(
                FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("Téléphone: 02.43.71.70.76", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("Télécopie:  02.43.71.70.94", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);

        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("SERAC France", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("12 Route de Mamers, BP 46,", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("72402 La Ferté-Bernard Cedex", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("N° Intracommunautaire: FR 63 340 321 801", FontFactory.getFont(
                FontFactory.TIMES_ROMAN, 12)));

        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        return table;
    }

    private Element createInfoCommand(String referenceCommand, String invoiceNumber) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setSpacingBefore(20);
        paragraph.add(new Phrase("BON DE LIVRAISON", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 14)));

        float[] colsWidth = { 25f, 30f, 45f };
        PdfPTable table = new PdfPTable(colsWidth);
        table.setWidthPercentage(50);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell headerCell = new PdfPCell(new Phrase("Numéro"));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setMinimumHeight(20);
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Date"));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setMinimumHeight(20);
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Commande Client"));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setMinimumHeight(20);
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(headerCell);

        PdfPCell cell = new PdfPCell(new Phrase(invoiceNumber, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(SimpleDateFormat.getDateInstance().format(new Date()), FontFactory.getFont(
                FontFactory.TIMES_ROMAN, 12)));
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(referenceCommand, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
        cell.setMinimumHeight(20);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        paragraph.add(table);

        return paragraph;
    }

    private Element createCommandTable(List<OrderDetailData> commands) {
        float[] colsWidth = { 7f, 23f, 60f, 10f };
        PdfPTable table = new PdfPTable(colsWidth);
        table.setWidthPercentage(100);
        table.setSpacingBefore(40);

        PdfPCell headerCell = new PdfPCell(new Phrase("Ligne"));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setMinimumHeight(20);
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Article"));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setMinimumHeight(20);
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Description"));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setMinimumHeight(20);
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Quantité"));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setMinimumHeight(20);
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(headerCell);

        PdfPCell cell;
        for (OrderDetailData command : commands) {
            // if(command.isSelected()) {
            cell = new PdfPCell(new Phrase(String.valueOf(command.getLine())));
            cell.setMinimumHeight(20);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(command.getReference()));
            cell.setMinimumHeight(20);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(command.getDescription()));
            cell.setMinimumHeight(20);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(new Integer(command.getQuantity()).toString()));
            cell.setMinimumHeight(20);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);
            // }
        }
        return table;
    }
}
