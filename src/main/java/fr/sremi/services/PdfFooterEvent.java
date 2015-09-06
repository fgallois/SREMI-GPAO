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

public class PdfFooterEvent extends PdfPageEventHelper {

    public void onEndPage(PdfWriter writer, Document document) {
        try {
            Rectangle page = document.getPageSize();
            buildFooter(page, document, writer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void buildFooter(Rectangle page, Document document, PdfWriter writer) {

        float[] columnWidths = { 1f };
        PdfPTable footer = new PdfPTable(columnWidths);

        footer.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        PdfPCell cell = new PdfPCell(
                new Phrase(
                        "Les mat�riels livr�s restent la propri�t� du vendeur jusqu'au paiement"
                                + " int�gral du prix par l'acheteur (loi n�80335 du 12 mai 1980)."
                                + " En cas de r�glement avant la date d'�ch�ance convenue, aucun escompte ne sera accord�; "
                                + "pour tout d�passement de l'�ch�ance convenue, un int�r�t de retard de 1,3% par mois sera appliqu�,"
                                + " apr�s mise en demeure par lettre recommand�e.", FontFactory.getFont(
                                FontFactory.TIMES_ROMAN, 8)));
        cell.setBorder(Rectangle.NO_BORDER);
        footer.addCell(cell);

        LineSeparator footerLine = getLineSeparator();
        footerLine.drawLine(writer.getDirectContent(), document.left(), document.right(), document.bottom() + 21);
        footer.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin() + 20, writer.getDirectContent());
    }

    private LineSeparator getLineSeparator() {
        LineSeparator line = new LineSeparator();
        line.setLineColor(BaseColor.LIGHT_GRAY);
        line.setLineWidth(.5f);
        return line;
    }

}
