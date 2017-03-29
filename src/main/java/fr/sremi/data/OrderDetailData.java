package fr.sremi.data;

import fr.sremi.model.LineItem;

import java.util.Date;

/**
 * Created by fgallois on 9/6/15.
 */
public class OrderDetailData {

    private Long id;
    private int line;
    private String reference;
    private String description;
    private int quantity;
    private Date dueDate;
    private Double unitPriceHT;

    public OrderDetailData() {
    }

    public OrderDetailData(LineItem lineItem) {
        this.id = lineItem.getId();
        this.line = lineItem.getLine();
        this.reference = lineItem.getPart().getReference();
        this.description = lineItem.getPart().getDescription();
        this.quantity = lineItem.getQuantity();
        this.dueDate = lineItem.getDueDate();
        this.unitPriceHT = lineItem.getUnitPrice() == null ? Double.valueOf(0) : lineItem.getUnitPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
