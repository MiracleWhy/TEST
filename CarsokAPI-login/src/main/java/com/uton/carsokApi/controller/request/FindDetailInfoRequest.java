package com.uton.carsokApi.controller.request;

import lombok.Data;

@Data
public class FindDetailInfoRequest {
    private String tenureCarId;
    private int accountId;
    private int childId;

    public String getTenureCarId() {
        return tenureCarId;
    }

    public void setTenureCarId(String tenureCarId) {
        this.tenureCarId = tenureCarId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }
}
