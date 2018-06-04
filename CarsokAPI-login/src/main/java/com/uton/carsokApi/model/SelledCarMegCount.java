package com.uton.carsokApi.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28/028.
 */
public class SelledCarMegCount {
    List<SelledCarMeg> list;
    private Double income;//总收入
    private Double profit;//总盈利
    private int carcount;//车辆总数
    private int carCountMonth;//本月售车

    public int getCarCountMonth() {
        return carCountMonth;
    }

    public void setCarCountMonth(int carCountMonth) {
        this.carCountMonth = carCountMonth;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public int getCarcount() {
        return carcount;
    }

    public void setCarcount(int carcount) {
        this.carcount = carcount;
    }

    public List<SelledCarMeg> getList() {
        return list;
    }

    public void setList(List<SelledCarMeg> list) {
        this.list = list;
    }
}
