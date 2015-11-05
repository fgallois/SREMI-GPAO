package fr.sremi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by fgallois on 11/4/15.
 */
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "reference", unique = true)
    private String reference;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_lineItems", joinColumns = @JoinColumn(name = "orderId"), inverseJoinColumns = @JoinColumn(name = "lineItemId"))
    private List<LineItem> lineItems;

    protected Order() {
    }

    public Order(String reference) {
        this.reference = reference;
        this.lineItems = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void addLineItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }
}
