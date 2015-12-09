package fr.sremi.data.invoice;

import java.util.Date;

/**
 * Created by fgallois on 12/8/15.
 */
public class ReceiptData {
    private Long id;
    private int number;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
