package com.uton.carsokApi.controller.response;

import java.util.Comparator;

/**
 * Created by SEELE on 2017/11/10.
 */
public class PotCusVoResponse implements Comparator{
    private String consultant; //顾问
    private Integer ranking; //排名 |
    private Integer potCusBaseCount;//个人基盘
    private Integer potCusAddCount;//个人留档
    private Integer potCusFaillCount;//个人战败
    private Integer potCusTotalCount;//个人潜客总数

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getPotCusBaseCount() {
        return potCusBaseCount;
    }

    public void setPotCusBaseCount(Integer potCusBaseCount) {
        this.potCusBaseCount = potCusBaseCount;
    }

    public Integer getPotCusAddCount() {
        return potCusAddCount;
    }

    public void setPotCusAddCount(Integer potCusAddCount) {
        this.potCusAddCount = potCusAddCount;
    }

    public Integer getPotCusFaillCount() {
        return potCusFaillCount;
    }

    public void setPotCusFaillCount(Integer potCusFaillCount) {
        this.potCusFaillCount = potCusFaillCount;
    }

    public Integer getPotCusTotalCount() {
        return potCusTotalCount;
    }

    public void setPotCusTotalCount(Integer potCusTotalCount) {
        this.potCusTotalCount = potCusTotalCount;
    }

    @Override
    public int compare(Object o1, Object o2) {
        PotCusVoResponse object1=(PotCusVoResponse)o1;
        PotCusVoResponse object2=(PotCusVoResponse)o2;
        return object2.getPotCusAddCount().compareTo(object1.getPotCusAddCount());
    }
}
