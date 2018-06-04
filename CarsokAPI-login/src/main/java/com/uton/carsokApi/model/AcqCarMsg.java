package com.uton.carsokApi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by SEELE on 2017/6/20.
 */
public class AcqCarMsg implements Serializable{
    int id;//收车id
    String carName;//车名
    Date firstUpTime;//首次上牌日期
    double miles;
    BigDecimal closeingPrice;//收车价格
    String acqer;//收车员
    private Boolean accountZanIf;//主帐号是否赞
    private Boolean zjlZanIf;//总经理是否赞
    String acqerPhone;//收车员电话
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

    public String getAcqerPhone() {
        return acqerPhone;
    }

    public void setAcqerPhone(String acqerPhone) {
        this.acqerPhone = acqerPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Date getFirstUpTime() {
        return firstUpTime;
    }

    public void setFirstUpTime(Date firstUpTime) {
        this.firstUpTime = firstUpTime;
    }

    public BigDecimal getCloseingPrice() {
        return closeingPrice;
    }

    public void setCloseingPrice(BigDecimal closeingPrice) {
        this.closeingPrice = closeingPrice;
    }

    public String getAcqer() {
        return acqer;
    }

    public void setAcqer(String acqer) {
        this.acqer = acqer;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }
}
