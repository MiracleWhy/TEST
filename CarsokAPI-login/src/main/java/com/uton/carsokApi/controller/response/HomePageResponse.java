package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/11/8.
 */
@Data
public class HomePageResponse implements Serializable{

    private boolean managerOrNot;
    private Integer dailySaledCar;//本日已售辆车
    private Integer dailyAddCus;//本日新增留档人数
    private Integer dailyFailPotCus;//战败人数
    private Integer totalPotCus;//潜客总人数
    private Integer NLevelCount;//当前N级人数
    private Integer HLevelCount;//当前H级人数
    private Integer ALevelCount;//当前A级人数
    private Integer BLevelCount;//当前B级人数
    private Integer CLevelCount;//当前C级人数
    private Integer FLevelCount;//当前F级人数
    private Integer FOCount;//FO人数
    private String sourceRanking;//今日占比最高来源
    private Integer dailyPotFollowCount;//今日潜客已跟进次数
    private boolean isDailyRepPub;//今日是否已发送日报
    private Integer dailyRepPubCount;//今日员工日报已发人数
    private Integer dailyRepNoPubCount;//今日员工日报未发人数

    public boolean isManagerOrNot() {
        return managerOrNot;
    }

    public void setManagerOrNot(boolean managerOrNot) {
        this.managerOrNot = managerOrNot;
    }

    public boolean isDailyRepPub() {
        return isDailyRepPub;
    }

    public void setDailyRepPub(boolean dailyRepPub) {
        isDailyRepPub = dailyRepPub;
    }

    public Integer getDailySaledCar() {
        return dailySaledCar;
    }

    public void setDailySaledCar(Integer dailySaledCar) {
        this.dailySaledCar = dailySaledCar;
    }

    public Integer getDailyAddCus() {
        return dailyAddCus;
    }

    public void setDailyAddCus(Integer dailyAddCus) {
        this.dailyAddCus = dailyAddCus;
    }

    public Integer getDailyFailPotCus() {
        return dailyFailPotCus;
    }

    public void setDailyFailPotCus(Integer dailyFailPotCus) {
        this.dailyFailPotCus = dailyFailPotCus;
    }

    public Integer getTotalPotCus() {
        return totalPotCus;
    }

    public void setTotalPotCus(Integer totalPotCus) {
        this.totalPotCus = totalPotCus;
    }

    public Integer getNLevelCount() {
        return NLevelCount;
    }

    public void setNLevelCount(Integer NLevelCount) {
        this.NLevelCount = NLevelCount;
    }

    public Integer getHLevelCount() {
        return HLevelCount;
    }

    public void setHLevelCount(Integer HLevelCount) {
        this.HLevelCount = HLevelCount;
    }

    public Integer getALevelCount() {
        return ALevelCount;
    }

    public void setALevelCount(Integer ALevelCount) {
        this.ALevelCount = ALevelCount;
    }

    public Integer getBLevelCount() {
        return BLevelCount;
    }

    public void setBLevelCount(Integer BLevelCount) {
        this.BLevelCount = BLevelCount;
    }

    public Integer getCLevelCount() {
        return CLevelCount;
    }

    public void setCLevelCount(Integer CLevelCount) {
        this.CLevelCount = CLevelCount;
    }

    public Integer getFLevelCount() {
        return FLevelCount;
    }

    public void setFLevelCount(Integer FLevelCount) {
        this.FLevelCount = FLevelCount;
    }

    public Integer getFOCount() {
        return FOCount;
    }

    public void setFOCount(Integer FOCount) {
        this.FOCount = FOCount;
    }

    public String getSourceRanking() {
        return sourceRanking;
    }

    public void setSourceRanking(String sourceRanking) {
        this.sourceRanking = sourceRanking;
    }

    public Integer getDailyPotFollowCount() {
        return dailyPotFollowCount;
    }

    public void setDailyPotFollowCount(Integer dailyPotFollowCount) {
        this.dailyPotFollowCount = dailyPotFollowCount;
    }

    public Integer getDailyRepPubCount() {
        return dailyRepPubCount;
    }

    public void setDailyRepPubCount(Integer dailyRepPubCount) {
        this.dailyRepPubCount = dailyRepPubCount;
    }

    public Integer getDailyRepNoPubCount() {
        return dailyRepNoPubCount;
    }

    public void setDailyRepNoPubCount(Integer dailyRepNoPubCount) {
        this.dailyRepNoPubCount = dailyRepNoPubCount;
    }
}
