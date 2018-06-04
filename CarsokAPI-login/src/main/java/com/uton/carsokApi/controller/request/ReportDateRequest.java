package com.uton.carsokApi.controller.request;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/11/9.
 */
public class ReportDateRequest implements Serializable{
    String date;
    Integer type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
