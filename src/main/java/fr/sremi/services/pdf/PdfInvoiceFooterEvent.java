package fr.sremi.services.pdf;

import java.text.SimpleDateFormat;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import fr.sremi.util.InvoiceUtils;

public class PdfInvoiceFooterEvent extends PdfPageEventHelper {

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
                        "Les matèriels livrés restent la propriété du vendeur jusqu'au paiement"
                                + " intégral du prix par l'acheteur (loi n°80335 du 12 mai 1980)."
                                + " En cas de réglement avant la date d'échéance convenue, aucun escompte ne sera accordé; "
                                + "pour tout dépassement de l'échéance convenue, un intérêt de retard de 1,3% par mois sera appliqué,"
                                + " après mise en demeure par lettre recommandée.", FontFactory.getFont(
                                FontFactory.TIMES_ROMAN, 8)));
        cell.setBorder(Rectangle.NO_BORDER);
        footer.addCell(cell);

        PdfPCell echeanceTableCell = new PdfPCell();
        echeanceTableCell.addElement(getEcheanceTable());
        footer.addCell(echeanceTableCell);

        LineSeparator footerLine = getLineSeparator();
        footerLine.drawLine(writer.getDirectContent(), document.left(), document.right(), document.bottom() + 31);
        footer.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin() + 30, writer.getDirectContent());
    }

    private LineSeparator getLineSeparator() {
        LineSeparator line = new LineSeparator();
        line.setLineColor(BaseColor.LIGHT_GRAY);
        line.setLineWidth(.5f);
        return line;
    }

    private Element getEcheanceTable() {
        float[] columWidths = { 0.2f, 0.5f, 0.2f };
        PdfPTable echeance = new PdfPTable(columWidths);
        echeance.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell(new Phrase("Conditions de règlement:", FontFactory.getFont(FontFactory.TIMES_BOLD,
                10)));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        echeance.addCell(cell);

        cell = new PdfPCell(new Phrase("Virement à échéance 45 jours fin de mois", FontFactory.getFont(
                FontFactory.TIMES_ROMAN, 10)));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        echeance.addCell(cell);

        cell = new PdfPCell(new Phrase("échéance le "
                + new SimpleDateFormat("dd/MM/yyyy").format(InvoiceUtils.currentEcheanceDate().toDate()),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        echeance.addCell(cell);

        return echeance;
    }
}
