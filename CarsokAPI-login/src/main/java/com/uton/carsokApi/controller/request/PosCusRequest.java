package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by SEELE on 2017/12/13.
 */
@Data
public class PosCusRequest {

    private int custId;
    //    车型
    private String brand;

    //    车系
    private String series;

    //    描述
    private String model;

    private int account_id;

    private int child_id;

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }
}
