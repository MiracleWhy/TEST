package com.uton.carsokApi.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8/008.
 */
public class CarForgSale {
    private Integer saleSum;//销售总量
    private Integer saleTotalSum;//销售总计
    private Double chainSum;//总量环比
    private String upOrDownSum;//向上向下箭头
    private List<CarForgSaleOne> rows;


    public Integer getSaleSum() {
        return saleSum;
    }

    public void setSaleSum(Integer saleSum) {
        this.saleSum = saleSum;
    }

    public Integer getSaleTotalSum() {
        return saleTotalSum;
    }

    public void setSaleTotalSum(Integer saleTotalSum) {
        this.saleTotalSum = saleTotalSum;
    }

    public Double getChainSum() {
        return chainSum;
    }

    public void setChainSum(Double chainSum) {
        this.chainSum = chainSum;
    }

    public String getUpOrDownSum() {
        return upOrDownSum;
    }

    public void setUpOrDownSum(String upOrDownSum) {
        this.upOrDownSum = upOrDownSum;
    }

    public List<CarForgSaleOne> getRows() {
        return rows;
    }

    public void setRows(List<CarForgSaleOne> rows) {
        this.rows = rows;
    }
}
