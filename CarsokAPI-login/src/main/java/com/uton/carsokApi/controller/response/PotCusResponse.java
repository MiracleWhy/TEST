package com.uton.carsokApi.controller.response;

import java.util.List;

/**
 * Created by SEELE on 2017/11/9.
 */
public class PotCusResponse {
    private List<PotCusVoResponse> rows;//个人潜客列表
    private Integer potCusBaseSum;//基盘总和
    private Integer potCusAddSum;//留档总和
    private Integer potCusFaillSum;//战败总和
    private Integer potCusTotalSum;//潜客总数总和

    public List<PotCusVoResponse> getRows() {
        return rows;
    }

    public void setRows(List<PotCusVoResponse> rows) {
        this.rows = rows;
    }

    public Integer getPotCusBaseSum() {
        return potCusBaseSum;
    }

    public void setPotCusBaseSum(Integer potCusBaseSum) {
        this.potCusBaseSum = potCusBaseSum;
    }

    public Integer getPotCusAddSum() {
        return potCusAddSum;
    }

    public void setPotCusAddSum(Integer potCusAddSum) {
        this.potCusAddSum = potCusAddSum;
    }

    public Integer getPotCusFaillSum() {
        return potCusFaillSum;
    }

    public void setPotCusFaillSum(Integer potCusFaillSum) {
        this.potCusFaillSum = potCusFaillSum;
    }

    public Integer getPotCusTotalSum() {
        return potCusTotalSum;
    }

    public void setPotCusTotalSum(Integer potCusTotalSum) {
        this.potCusTotalSum = potCusTotalSum;
    }
}
