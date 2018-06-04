package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/18/018.
 */
public class SelectSaleCar {
    private String id;
    private int miles;
    private String productName;
    private String picture;
    private Date firstUpTime;
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getFirstUpTime() {
        return firstUpTime;
    }

    public void setFirstUpTime(Date firstUpTime) {
        this.firstUpTime = firstUpTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
