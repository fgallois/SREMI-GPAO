package fr.sremi.services;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class PdfHeaderEvent extends PdfPageEventHelper {

    public void onEndPage(PdfWriter writer, Document document) {
        if (document.getPageNumber() == 2) {
            try {
                Rectangle page = document.getPageSize();
                buildHeader(page, document, writer);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void buildHeader(Rectangle page, Document document, PdfWriter writer) {

        float[] columnWidths = { 1f };
        PdfPTable header = new PdfPTable(columnWidths);

        header.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        PdfPCell cell = new PdfPCell(new Phrase("Exemplaire SREMI", FontFactory.getFont(FontFactory.TIMES_ITALIC, 12)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        header.addCell(cell);

        LineSeparator headerLine = getLineSeparator();
        headerLine.drawLine(writer.getDirectContent(), document.left(), document.right(), document.top() + 5);
        header.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - 10f, writer.getDirectContent());
    }

    private LineSeparator getLineSeparator() {
        LineSeparator line = new LineSeparator();
        line.setLineColor(BaseColor.LIGHT_GRAY);
        line.setLineWidth(.5f);
        return line;
    }

}
