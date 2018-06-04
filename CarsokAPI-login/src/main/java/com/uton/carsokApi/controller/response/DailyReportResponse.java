package com.uton.carsokApi.controller.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by SEELE on 2017/6/21.
 */
public class DailyReportResponse implements Serializable {

    private Integer selledMonCount;//本月卖出数
    private Integer selledDayCount;//本日卖出数
    private Double selledDayAmount;//日营收
    private Double dayProfit;//日毛利润
    private Integer orderedCount;//已定车数
    private Double orderAmount;//定金
    private Integer acqMonCount;//本月收车数
    private Integer acqDayCount;//本日收车数
    private Double acqDayAmount;//日支出
    private Integer zbMonCount;//本月整备数
    private Integer zbDayCount;//本日整备数
    private Double zbDayAmount;//整备日支出
    private Integer receptionCount;//接待人数
    private String merchantName;//车行名

    public Integer getSelledMonCount() {
        return selledMonCount;
    }

    public void setSelledMonCount(Integer selledMonCount) {
        this.selledMonCount = selledMonCount;
    }

    public Integer getSelledDayCount() {
        return selledDayCount;
    }

    public void setSelledDayCount(Integer selledDayCount) {
        this.selledDayCount = selledDayCount;
    }

    public Double getSelledDayAmount() {
        return selledDayAmount;
    }

    public void setSelledDayAmount(Double selledDayAmount) {
        this.selledDayAmount = selledDayAmount;
    }

    public Double getDayProfit() {
        return dayProfit;
    }

    public void setDayProfit(Double dayProfit) {
        this.dayProfit = dayProfit;
    }

    public Integer getOrderedCount() {
        return orderedCount;
    }

    public void setOrderedCount(Integer orderedCount) {
        this.orderedCount = orderedCount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getAcqMonCount() {
        return acqMonCount;
    }

    public void setAcqMonCount(Integer acqMonCount) {
        this.acqMonCount = acqMonCount;
    }

    public Integer getAcqDayCount() {
        return acqDayCount;
    }

    public void setAcqDayCount(Integer acqDayCount) {
        this.acqDayCount = acqDayCount;
    }

    public Double getAcqDayAmount() {
        return acqDayAmount;
    }

    public void setAcqDayAmount(Double acqDayAmount) {
        this.acqDayAmount = acqDayAmount;
    }

    public Integer getZbMonCount() {
        return zbMonCount;
    }

    public void setZbMonCount(Integer zbMonCount) {
        this.zbMonCount = zbMonCount;
    }

    public Integer getZbDayCount() {
        return zbDayCount;
    }

    public void setZbDayCount(Integer zbDayCount) {
        this.zbDayCount = zbDayCount;
    }

    public Double getZbDayAmount() {
        return zbDayAmount;
    }

    public void setZbDayAmount(Double zbDayAmount) {
        this.zbDayAmount = zbDayAmount;
    }

    public Integer getReceptionCount() {
        return receptionCount;
    }

    public void setReceptionCount(Integer receptionCount) {
        this.receptionCount = receptionCount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
