package com.buxz.base;

import java.io.Serializable;

/**
 * Created by SQ_BXZ on 2018-12-14.
 */
public class BaseEntity implements Serializable {

    /* 分页页码 ，默认 1 */
    protected int page = 1;
    /* 分页每页数量，默认 20 */
    protected int size = 20;
    /* 排序列名称 ， 默认 id */
    protected String sidx= "id";
    /* 排序算法 默认正序 */
    protected String sord= "asc";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }
}
