package com.uton.carsokApi.controller.request;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/7/11.
 */
public class DailyReportSaleRequest implements Serializable{
    String date;
    String childId;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
