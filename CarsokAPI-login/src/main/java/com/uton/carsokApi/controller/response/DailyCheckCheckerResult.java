package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.model.Product;

/**
 * Created by SEELE on 2017/9/19.
 */
public class DailyCheckCheckerResult extends Product {
    private String checkerName;
    private String picPath;
    private String firstUpTime;
    private String checker;
    private String miles;

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

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

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }
}
