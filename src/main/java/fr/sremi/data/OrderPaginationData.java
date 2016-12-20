package fr.sremi.data;

import java.util.List;

/**
 * Created by fgallois on 9/29/15.
 */
public class OrderPaginationData {
    private long total;
    private List<OrderData> rows;

    public OrderPaginationData(long total, List<OrderData> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<OrderData> getRows() {
        return rows;
    }

    public void setRows(List<OrderData> rows) {
        this.rows = rows;
    }
}
