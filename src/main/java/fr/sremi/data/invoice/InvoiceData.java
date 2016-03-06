package fr.sremi.data.invoice;

import java.util.ArrayList;
import java.util.List;

import fr.sremi.data.OrderDetailData;

/**
 * Created by fgallois on 12/8/15.
 */
public class InvoiceData {
    private String reference;
    private String certificateNumber;
    private Boolean withVat;
    private Double vatRate;
    private List<ReceiptData> receipts;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Boolean getWithVat() {
        return withVat;
    }

    public void setWithVat(Boolean withVat) {
        this.withVat = withVat;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }

    public List<ReceiptData> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<ReceiptData> receipts) {
        this.receipts = receipts;
    }

    public List<OrderDetailData> getAllOrderDetails() {
        List<OrderDetailData> result = new ArrayList<>();
        for (ReceiptData receipt : receipts) {
            result.addAll(receipt.getOrderDetails());
        }
        return result;
    }
}
