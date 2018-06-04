package com.uton.carsokApi.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20/020.
 */
public class OverdueForgModel {
    private String name;//名字
    private Integer dayOverdue;//当日逾期
    private Integer retainOverdue;//保有客户逾期
    private Integer potentialOverdue;//潜在客户逾期
    private List<OverdueForgModelOne> rows;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<OverdueForgModelOne> getRows() {
        return rows;
    }

    public void setRows(List<OverdueForgModelOne> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDayOverdue() {
        return dayOverdue;
    }

    public void setDayOverdue(Integer dayOverdue) {
        this.dayOverdue = dayOverdue;
    }

    public Integer getRetainOverdue() {
        return retainOverdue;
    }

    public void setRetainOverdue(Integer retainOverdue) {
        this.retainOverdue = retainOverdue;
    }

    public Integer getPotentialOverdue() {
        return potentialOverdue;
    }

    public void setPotentialOverdue(Integer potentialOverdue) {
        this.potentialOverdue = potentialOverdue;
    }
}
