package fr.sremi.data;

/**
 * Created by fgallois on 9/6/15.
 */
public class OrderData {
    private Integer id;
    private String orderReference;

    public OrderData(Integer id, String orderReference) {
        this.id = id;
        this.orderReference = orderReference;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }
}
