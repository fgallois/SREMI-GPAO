package fr.sremi.data.invoice;

import fr.sremi.data.OrderDetailData;

import java.util.Date;
import java.util.List;

/**
 * Created by fgallois on 12/8/15.
 */
public class ReceiptData {
    private Long id;
    private int number;
    private Date creationDate;
    private List<OrderDetailData> orderDetails;


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

    public List<OrderDetailData> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailData> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
