package com.baizhi.ViewObject;

public class Paging {
    private Integer page;
    private Integer rows;
    private Integer total;
    private Integer totalPage;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }



    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "page=" + page +
                ", rows=" + rows +
                ", total=" + total +
                ", totalPage=" + totalPage +
                '}';
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Paging(Integer page, Integer rows, Integer total, Integer totalPage) {
        this.page = page;
        this.rows = rows;
        this.total = total;
        this.totalPage = totalPage;
    }

    public Paging() {
    }
}
