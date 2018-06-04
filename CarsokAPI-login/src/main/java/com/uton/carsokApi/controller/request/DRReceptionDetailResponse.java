package com.uton.carsokApi.controller.request;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/6/20.
 */
public class DRReceptionDetailResponse implements Serializable{
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    private String customerName;
    private String customerPhone;
    private String customerType;
    private Integer id;
    private Boolean accountZanIf;//主帐号是否赞
    private Boolean zjlZanIf;//总经理是否赞
    private Integer zanId;

    public Integer getZanId() {
        return zanId;
    }

    public void setZanId(Integer zanId) {
        this.zanId = zanId;
    }

    public Boolean getZjlZanIf() {
        return zjlZanIf;
    }

    public void setZjlZanIf(Boolean zjlZanIf) {
        this.zjlZanIf = zjlZanIf;
    }

    public Boolean getAccountZanIf() {
        return accountZanIf;
    }

    public void setAccountZanIf(Boolean accountZanIf) {
        this.accountZanIf = accountZanIf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
