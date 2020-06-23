package base.mybatis;

import java.util.List;

public class PageResult<T> {
    private long total;

    private List<T> data;
    public PageResult(){

    }

    public PageResult(long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
