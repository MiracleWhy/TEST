package com.uton.carsokApi.controller.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/12.
 */
public class BargainInfoResponse {
    private Integer id;
    private Integer productId;
    private String mobile;
    private String name;
    private String intentionalPrice;
    private String picture;
    private String productName;
    private BigDecimal price;
    private BigDecimal miniPrice;
    private Integer miles;
    private Date firstUpTime;
    private Date onShelfTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntentionalPrice() {
        return intentionalPrice;
    }

    public void setIntentionalPrice(String intentionalPrice) {
        this.intentionalPrice = intentionalPrice;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMiniPrice() {
        return miniPrice;
    }

    public void setMiniPrice(BigDecimal miniPrice) {
        this.miniPrice = miniPrice;
    }

    public Integer getMiles() {
        return miles;
    }

    public void setMiles(Integer miles) {
        this.miles = miles;
    }

    public Date getFirstUpTime() {
        return firstUpTime;
    }

    public void setFirstUpTime(Date firstUpTime) {
        this.firstUpTime = firstUpTime;
    }

    public Date getOnShelfTime() {
        return onShelfTime;
    }

    public void setOnShelfTime(Date onShelfTime) {
        this.onShelfTime = onShelfTime;
    }
}
