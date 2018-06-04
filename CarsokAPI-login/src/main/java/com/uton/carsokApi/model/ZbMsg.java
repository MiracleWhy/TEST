package com.uton.carsokApi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by SEELE on 2017/6/21.
 */
public class ZbMsg implements Serializable{
    private int id;//task_id
    private String carName;//车名
    private Date firstUpDate;//初次上牌日期
    private double miles;//公里数
    private BigDecimal zbMoney;//整备费
    private String zber;//整备员名字
    private String accountPhone;//整备账户电话
    private int zbDay;
    private Boolean accountZanIf;//主帐号是否赞
    private Boolean zjlZanIf;//总经理是否赞
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

    public int getZbDay() {
        return zbDay;
    }

    public void setZbDay(int zbDay) {
        this.zbDay = zbDay;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
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

    public Date getFirstUpDate() {
        return firstUpDate;
    }

    public void setFirstUpDate(Date firstUpDate) {
        this.firstUpDate = firstUpDate;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public BigDecimal getZbMoney() {
        return zbMoney;
    }

    public void setZbMoney(BigDecimal zbMoney) {
        this.zbMoney = zbMoney;
    }

    public String getZber() {
        return zber;
    }

    public void setZber(String zber) {
        this.zber = zber;
    }
}
