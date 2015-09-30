package fr.sremi.data;

import java.util.List;

/**
 * Created by fgallois on 9/29/15.
 */
public class PartPaginationData {
    private long total;
    private List<PartData> rows;

    public PartPaginationData(long total, List<PartData> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<PartData> getRows() {
        return rows;
    }

    public void setRows(List<PartData> rows) {
        this.rows = rows;
    }
}
