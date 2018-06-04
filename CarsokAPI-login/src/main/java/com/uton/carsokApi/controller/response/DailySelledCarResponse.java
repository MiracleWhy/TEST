package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.model.SelledCarMeg;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by SEELE on 2017/6/20.
 */
public class DailySelledCarResponse {
    int selledCount;//售出车辆数
    BigDecimal selledAmount;//营收
    BigDecimal profit;//总毛利润
    List<SelledCarMeg> selledCarList;

    public int getSelledCount() {
        return selledCount;
    }

    public void setSelledCount(int selledCount) {
        this.selledCount = selledCount;
    }

    public BigDecimal getSelledAmount() {
        return selledAmount;
    }

    public void setSelledAmount(BigDecimal selledAmount) {
        this.selledAmount = selledAmount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public List<SelledCarMeg> getSelledCarList() {
        return selledCarList;
    }

    public void setSelledCarList(List<SelledCarMeg> selledCarList) {
        this.selledCarList = selledCarList;
    }
}
