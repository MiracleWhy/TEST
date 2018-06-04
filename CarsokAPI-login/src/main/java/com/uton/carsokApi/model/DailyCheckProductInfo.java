package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

public class DailyCheckProductInfo extends Product{

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String checker;
    private String productId;
    private String checkerName;
    private String isCheck;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getFirstUpTime() {
        return firstUpTime;
    }

    public void setFirstUpTime(String firstUpTime) {
        this.firstUpTime = firstUpTime;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    private String picPath;
    private String firstUpTime;
    private String miles;


    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }
}