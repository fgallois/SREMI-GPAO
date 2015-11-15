package fr.sremi.data;

import java.util.Date;

/**
 * Created by fgallois on 9/6/15.
 */
public class OrderDetailData {

    private int line;
    private String reference;
    private String description;
    private int quantity;
    private Date dueDate;
    private Double unitPriceHT;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getUnitPriceHT() {
        return unitPriceHT;
    }

    public void setUnitPriceHT(Double unitPriceHT) {
        this.unitPriceHT = unitPriceHT;
    }
}
