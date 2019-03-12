package com.taotao.pojo;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.List;

//easyui前端框架数据格式的结果集
public class EUDataGridResult {

    //数据总数
    private long total;

    //数据集
    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
