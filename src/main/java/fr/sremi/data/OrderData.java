package fr.sremi.data;

/**
 * Created by fgallois on 9/6/15.
 */
public class OrderData {
    private Long id;
    private String orderReference;
    private Boolean status;
    private String buyerName;

    public OrderData(Long id, String orderReference, Boolean status, String buyerName) {
        this.id = id;
        this.orderReference = orderReference;
        this.status = status;
        this.buyerName = buyerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
