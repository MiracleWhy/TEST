package com.uton.carsokApi.controller.request;

import com.uton.carsokApi.model.TaskZbBill;
import com.uton.carsokApi.model.ZbMoneyInfo;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
public class TaskForYyzyRequest {
    private String carName;
    private String carNum;
    private Integer id;
    private String lastCarNuml;
    private String remark;
    private String yyzyRemark;
    private Integer sxyid;
    private String infoMobile;
    private String infoName;
    private Integer infoSource;
    private Integer keysNum;
    private String sxyRemark;
    private String selfMobile;
    private String selfName;
    private Integer zbyid;
    private String zbyRemark;
    private BigDecimal zbMoney;
    private String overprice;
    private String selfprice;
    private String taskNum;
    private String taskTime;
    private BigDecimal buyPrice;
    private String vin;
    private List<ZbMoneyInfo> managerInfoList = new ArrayList<>();
    private List<TaskZbBill> billList;

    public List<TaskZbBill> getBillList() {
        return billList;
    }

    public void setBillList(List<TaskZbBill> billList) {
        this.billList = billList;
    }

    public List<ZbMoneyInfo> getManagerInfoList() {
        return managerInfoList;
    }

    public void setManagerInfoList(List<ZbMoneyInfo> managerInfoList) {
        this.managerInfoList = managerInfoList;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastCarNuml() {
        return lastCarNuml;
    }

    public void setLastCarNuml(String lastCarNuml) {
        this.lastCarNuml = lastCarNuml;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getYyzyRemark() {
        return yyzyRemark;
    }

    public void setYyzyRemark(String yyzyRemark) {
        this.yyzyRemark = yyzyRemark;
    }

    public Integer getSxyid() {
        return sxyid;
    }

    public void setSxyid(Integer sxyid) {
        this.sxyid = sxyid;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoMobile() {
        return infoMobile;
    }

    public void setInfoMobile(String infoMobile) {
        this.infoMobile = infoMobile;
    }

    public Integer getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(Integer infoSource) {
        this.infoSource = infoSource;
    }

    public Integer getKeysNum() {
        return keysNum;
    }

    public void setKeysNum(Integer keysNum) {
        this.keysNum = keysNum;
    }

    public String getSxyRemark() {
        return sxyRemark;
    }

    public void setSxyRemark(String sxyRemark) {
        this.sxyRemark = sxyRemark;
    }

    public String getSelfMobile() {
        return selfMobile;
    }

    public void setSelfMobile(String selfMobile) {
        this.selfMobile = selfMobile;
    }

    public String getSelfName() {
        return selfName;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }

    public Integer getZbyid() {
        return zbyid;
    }

    public void setZbyid(Integer zbyid) {
        this.zbyid = zbyid;
    }

    public String getZbyRemark() {
        return zbyRemark;
    }

    public void setZbyRemark(String zbyRemark) {
        this.zbyRemark = zbyRemark;
    }

    public BigDecimal getZbMoney() {
        return zbMoney;
    }

    public void setZbMoney(BigDecimal zbMoney) {
        this.zbMoney = zbMoney;
    }

    public String getOverprice() {
        return overprice;
    }

    public void setOverprice(String overprice) {
        this.overprice = overprice;
    }

    public String getSelfprice() {
        return selfprice;
    }

    public void setSelfprice(String selfprice) {
        this.selfprice = selfprice;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
