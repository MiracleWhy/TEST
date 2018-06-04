package com.uton.carsokApi.controller.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SEELE on 2017/12/12.
 */
public class DealReportResponse implements Serializable{

    private List<DealReportListResponse> rows;
    private Integer potCusCountSum;//潜客数量合计
    private Integer dealCountSum;//成交数合计
    private Double chainSum;//环比合计
    private String upOrDownSum;//箭头
    private Double dealRateSum;//成交率合计


    public Integer getDealCountSum() {
        return dealCountSum;
    }

    public void setDealCountSum(Integer dealCountSum) {
        this.dealCountSum = dealCountSum;
    }

    public Integer getPotCusCountSum() {
        return potCusCountSum;
    }

    public void setPotCusCountSum(Integer potCusCountSum) {
        this.potCusCountSum = potCusCountSum;
    }

    public Double getChainSum() {
        return chainSum;
    }

    public void setChainSum(Double chainSum) {
        this.chainSum = chainSum;
    }

    public Double getDealRateSum() {
        return dealRateSum;
    }

    public void setDealRateSum(Double dealRateSum) {
        this.dealRateSum = dealRateSum;
    }

    public List<DealReportListResponse> getRows() {
        return rows;
    }

    public void setRows(List<DealReportListResponse> rows) {
        this.rows = rows;
    }

    public String getUpOrDownSum() {
        return upOrDownSum;
    }

    public void setUpOrDownSum(String upOrDownSum) {
        this.upOrDownSum = upOrDownSum;
    }
}
