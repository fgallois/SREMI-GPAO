package fr.sremi.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
 * Created by fgallois on 11/8/15.
 */
@Entity
@Table(name = "RECEIPTS")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer number;

    @NotNull
    private Date creationDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "receipt_lineItems", joinColumns = @JoinColumn(name = "receiptId"), inverseJoinColumns = @JoinColumn(name = "lineItemId"))
    private List<LineItem> lineItems;

    protected Receipt() {
    }

    public Receipt(Integer number, List<LineItem> lineItems) {
        this.number = number;
        this.lineItems = lineItems;
        this.creationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
