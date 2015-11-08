package fr.sremi.data;

/**
 * Created by fgallois on 9/6/15.
 */
public class OrderData {
    private Long id;
    private String orderReference;

    public OrderData(Long id, String orderReference) {
        this.id = id;
        this.orderReference = orderReference;
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
}
