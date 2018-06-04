package com.uton.carsokApi.controller.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SEELE on 2017/12/14.
 */
public class RegionReportResponse implements Serializable{

    private List<RegionReportRowResponse> rows;
    private Integer customerNumSum;
    private Double chainSum;
    private String upOrDownSum;
    private Double rateSum;

    public List<RegionReportRowResponse> getRows() {
        return rows;
    }

    public void setRows(List<RegionReportRowResponse> rows) {
        this.rows = rows;
    }

    public Integer getCustomerNumSum() {
        return customerNumSum;
    }

    public void setCustomerNumSum(Integer customerNumSum) {
        this.customerNumSum = customerNumSum;
    }

    public Double getChainSum() {
        return chainSum;
    }

    public void setChainSum(Double chainSum) {
        this.chainSum = chainSum;
    }

    public Double getRateSum() {
        return rateSum;
    }

    public void setRateSum(Double rateSum) {
        this.rateSum = rateSum;
    }

    public String getUpOrDownSum() {
        return upOrDownSum;
    }

    public void setUpOrDownSum(String upOrDownSum) {
        this.upOrDownSum = upOrDownSum;
    }
}
