package com.uton.carsokApi.model;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/11/8/008.
 */
public class CarForgSaleOne implements Comparator {
    private Integer saleOneCount;//单人销售总量
    private Integer saleOneTotal;//单人销售总计
    private Double chain;//环比
    private String consultant;//顾问名称
    private String upOrDown;//上升或者下降箭头
    private Integer ranking;//排名

    public Integer getSaleOneCount() {
        return saleOneCount;
    }

    public void setSaleOneCount(Integer saleOneCount) {
        this.saleOneCount = saleOneCount;
    }

    public Integer getSaleOneTotal() {
        return saleOneTotal;
    }

    public void setSaleOneTotal(Integer saleOneTotal) {
        this.saleOneTotal = saleOneTotal;
    }

    public Double getChain() {
        return chain;
    }

    public void setChain(Double chain) {
        this.chain = chain;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        this.upOrDown = upOrDown;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public int compare(Object o1, Object o2) {
        CarForgSaleOne object1 = (CarForgSaleOne) o1;
        CarForgSaleOne object2 = (CarForgSaleOne) o2;
        return object2.getSaleOneCount().compareTo(object1.getSaleOneCount());
    }
}
