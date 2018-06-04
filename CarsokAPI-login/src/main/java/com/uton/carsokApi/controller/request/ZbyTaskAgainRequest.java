package com.uton.carsokApi.controller.request;

import com.uton.carsokApi.model.TaskZbBill;
import com.uton.carsokApi.model.ZbMoneyInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */
public class ZbyTaskAgainRequest {
    private Integer id;
    private Integer tid;
    private String zbMoney;
    private String vin;
    private String remark;
    private List<ZbMoneyInfo> minfos;
    private String lastCarNum;
    private List<String> billList;//单据List
    private Integer againTimes;//再次整备次数

    public Integer getAgainTimes() {
        return againTimes;
    }

    public void setAgainTimes(Integer againTimes) {
        this.againTimes = againTimes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getZbMoney() {
        return zbMoney;
    }

    public void setZbMoney(String zbMoney) {
        this.zbMoney = zbMoney;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ZbMoneyInfo> getMinfos() {
        return minfos;
    }

    public void setMinfos(List<ZbMoneyInfo> minfos) {
        this.minfos = minfos;
    }

    public String getLastCarNum() {
        return lastCarNum;
    }

    public void setLastCarNum(String lastCarNum) {
        this.lastCarNum = lastCarNum;
    }

    public List<String> getBillList() {
        return billList;
    }

    public void setBillList(List<String> billList) {
        this.billList = billList;
    }
}
