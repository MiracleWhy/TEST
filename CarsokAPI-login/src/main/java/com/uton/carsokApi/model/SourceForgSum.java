package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/11/13/013.
 */
public class SourceForgSum {
    private Integer peopleSum;
    private Double chainSum;

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

    private Double rateSum;
    private String UpAndDown;
    private List<SourceForgOne> rows;

    public String getUpAndDown() {
        return UpAndDown;
    }

    public void setUpAndDown(String upAndDown) {
        UpAndDown = upAndDown;
    }

    public Integer getPeopleSum() {
        return peopleSum;
    }

    public void setPeopleSum(Integer peopleSum) {
        this.peopleSum = peopleSum;
    }



    public List<SourceForgOne> getRows() {
        return rows;
    }

    public void setRows(List<SourceForgOne> rows) {
        this.rows = rows;
    }
}
