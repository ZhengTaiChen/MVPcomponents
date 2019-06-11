package com.wwzs.component.commonsdk.entity;

import java.io.Serializable;

public class PageBean implements Serializable {


    /**
     * total : 1
     * count : 1
     * more : 0
     */

    private String total;
    private int page;
    private String count;
    private String more;

    public PageBean(int page, int count) {
        this.page = page ;
        this.count = String.valueOf(count);
    }

    public PageBean(int page) {
        this.page = page ;
        this.count = String.valueOf(10);
    }


    public int getTotal() {
        return Integer.parseInt(total);
    }

    public void setTotal(int total) {
        this.total = String.valueOf(total);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return Integer.parseInt(count);
    }

    public void setCount(int count) {
        this.count = String.valueOf(count);
    }

    public int getMore() {
        return Integer.parseInt(more);
    }

    public void setMore(int more) {
        this.more = String.valueOf(more);
    }
}
