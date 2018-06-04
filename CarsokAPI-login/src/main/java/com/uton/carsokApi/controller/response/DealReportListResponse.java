package com.uton.carsokApi.controller.response;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by SEELE on 2017/12/12.
 */
public class DealReportListResponse implements Comparator,Serializable{

    private Integer ranking;//排名
    private String name;//销售
    private Integer potCusCount;//潜客数量
    private Integer dealCount;//成交数
    private Double chain;//环比
    private String upOrDown;//箭头
    private Double dealRate;//成交率


    public Integer getDealCount() {
        return dealCount;
    }

    public void setDealCount(Integer dealCount) {
        this.dealCount = dealCount;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPotCusCount() {
        return potCusCount;
    }

    public void setPotCusCount(Integer potCusCount) {
        this.potCusCount = potCusCount;
    }

    public Double getChain() {
        return chain;
    }

    public void setChain(Double chain) {
        this.chain = chain;
    }

    public Double getDealRate() {
        return dealRate;
    }

    public void setDealRate(Double dealRate) {
        this.dealRate = dealRate;
    }

    public String getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        this.upOrDown = upOrDown;
    }

    @Override
    public int compare(Object o1, Object o2) {
        DealReportListResponse object1=(DealReportListResponse)o1;
        DealReportListResponse object2=(DealReportListResponse)o2;
        return object2.getDealCount().compareTo(object1.getDealCount());
    }
}
