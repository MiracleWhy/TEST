package com.uton.carsokApi.controller.response;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/12/14.
 */
public class RegionReportRowResponse implements Serializable {

    private String region;
    private Integer customerNum;
    private Double chain;
    private String upOrDown;
    private Double rate;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(Integer customerNum) {
        this.customerNum = customerNum;
    }

    public Double getChain() {
        return chain;
    }

    public void setChain(Double chain) {
        this.chain = chain;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        this.upOrDown = upOrDown;
    }
}
