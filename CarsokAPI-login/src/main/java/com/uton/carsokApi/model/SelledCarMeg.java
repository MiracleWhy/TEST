package com.uton.carsokApi.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by SEELE on 2017/6/20.
 */
public class SelledCarMeg implements Serializable{
    int id;//商品id
    String productName;//车辆名称
    BigDecimal salePrice;//出售价格
    BigDecimal oneProfit;//毛利润
    BigDecimal closeingPrice;//收车价格
    BigDecimal zbMoney; //整备价钱
    String saledPeople;//销售人电话
    String picPath;//图片地址
    private Boolean accountZanIf;//主帐号是否赞
    private Boolean zjlZanIf;//总经理是否赞
    String salePeopleName;
    private Integer zanId;

    public Integer getZanId() {
        return zanId;
    }

    public void setZanId(Integer zanId) {
        this.zanId = zanId;
    }

    public Boolean getAccountZanIf() {
        return accountZanIf;
    }

    public void setAccountZanIf(Boolean accountZanIf) {
        this.accountZanIf = accountZanIf;
    }

    public Boolean getZjlZanIf() {
        return zjlZanIf;
    }

    public void setZjlZanIf(Boolean zjlZanIf) {
        this.zjlZanIf = zjlZanIf;
    }

    public String getSalePeopleName() {
        return salePeopleName;
    }

    public void setSalePeopleName(String salePeopleName) {
        this.salePeopleName = salePeopleName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getOneProfit() {
        return oneProfit;
    }

    public void setOneProfit(BigDecimal oneProfit) {
        this.oneProfit = oneProfit;
    }

    public BigDecimal getCloseingPrice() {
        return closeingPrice;
    }

    public void setCloseingPrice(BigDecimal closeingPrice) {
        this.closeingPrice = closeingPrice;
    }

    public String getSaledPeople() {return saledPeople;}

    public BigDecimal getZbMoney() {return zbMoney;}

    public void setZbMoney(BigDecimal zbMoney) {this.zbMoney = zbMoney;}

    public void setSaledPeople(String saledPeople) {this.saledPeople = saledPeople;}
}
