package com.uton.carsokApi.model;

/**
 * Created by Administrator on 2017/4/10 0010.
 */
public class StoreRemind {
    private String accountPhone;
    private String childPhone;
    private String customerPhone;
    private String customerName;
    private Integer mendianId;

    public Integer getMendianId() {
        return mendianId;
    }

    public void setMendianId(Integer mendianId) {
        this.mendianId = mendianId;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getChildPhone() {
        return childPhone;
    }

    public void setChildPhone(String childPhone) {
        this.childPhone = childPhone;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
