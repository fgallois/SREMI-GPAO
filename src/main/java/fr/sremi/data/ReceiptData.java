package fr.sremi.data;

import java.util.List;

/**
 * Created by fgallois on 9/7/15.
 */
public class ReceiptData {
    private String orderRef;
    private List<OrderDetailData> lines;

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public List<OrderDetailData> getLines() {
        return lines;
    }

    public void setLines(List<OrderDetailData> lines) {
        this.lines = lines;
    }
}
