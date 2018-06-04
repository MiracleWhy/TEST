package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
public class AcquisitionCar {
    private Integer id;
    private Date carInspectionTime;
    private String collectionType;
    private String customerName;
    private String contentNum;
    private BigDecimal preferPrice;
    private String infoRecourse;
    private String collectionArea;
    private String consultPrice;
    private Integer isDeal;
    private BigDecimal closeingPrice;
    private String remark;
    private Integer accountId;
    private Integer childId;
    private Integer productId;
    private Date createTime;
    private Integer enable;
    private List<AcquisitionConsult> consultList;
    private List<AcquisitionCarPricing> pricingList;
    private String pricingInfo;
    private String mobile;
    private String vin;
    private String brand;
    private String cardTime;
    private String firstUpTime;
    private String alias;
    private String childPhone;
    private String account;

    private String maintenance;
    private String evaluate;
    private String closedcarcontract;
    private String evaluatePrice;

    private String taskNum;
    private String infoName;
    private  String infoMobile;

    private String color;
    private List<String> carPic;
    private String carPicString;

    public String getPricingInfo() {
        return pricingInfo;
    }

    public void setPricingInfo(String pricingInfo) {
        this.pricingInfo = pricingInfo;
    }

    public List<AcquisitionCarPricing> getPricingList() {
        return pricingList;
    }

    public void setPricingList(List<AcquisitionCarPricing> pricingList) {
        this.pricingList = pricingList;
    }

    public String getEvaluatePrice() {
        return evaluatePrice;
    }

    public String getInfoMobile() {
        return infoMobile;
    }

    public void setEvaluatePrice(String evaluatePrice) {
        this.evaluatePrice = evaluatePrice;
    }

    public void setInfoMobile(String infoMobile) {
        this.infoMobile = infoMobile;
    }



    public String getEvaluate() {
        return evaluate;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getClosedcarcontract() {
        return closedcarcontract;
    }

    public void setClosedcarcontract(String closedcarcontract) {
        this.closedcarcontract = closedcarcontract;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }


    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChildPhone() {
        return childPhone;
    }

    public void setChildPhone(String childPhone) {
        this.childPhone = childPhone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFirstUpTime() {
        return firstUpTime;
    }

    public void setFirstUpTime(String firstUpTime) {
        this.firstUpTime = firstUpTime;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCardTime() {
        return cardTime;
    }

    public void setCardTime(String cardTime) {
        this.cardTime = cardTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getConsultPrice() {
        return consultPrice;
    }

    public void setConsultPrice(String consultPrice) {
        this.consultPrice = consultPrice;
    }

    public List<AcquisitionConsult> getConsultList() {
        return consultList;
    }

    public void setConsultList(List<AcquisitionConsult> consultList) {
        this.consultList = consultList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCarInspectionTime() {
        return carInspectionTime;
    }

    public void setCarInspectionTime(Date carInspectionTime) {
        this.carInspectionTime = carInspectionTime;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContentNum() {
        return contentNum;
    }

    public void setContentNum(String contentNum) {
        this.contentNum = contentNum;
    }

    public BigDecimal getPreferPrice() {
        return preferPrice;
    }

    public void setPreferPrice(BigDecimal preferPrice) {
        this.preferPrice = preferPrice;
    }

    public String getInfoRecourse() {
        return infoRecourse;
    }

    public void setInfoRecourse(String infoRecourse) {
        this.infoRecourse = infoRecourse;
    }

    public String getCollectionArea() {
        return collectionArea;
    }

    public void setCollectionArea(String collectionArea) {
        this.collectionArea = collectionArea;
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }

    public BigDecimal getCloseingPrice() {
        return closeingPrice;
    }

    public void setCloseingPrice(BigDecimal closeingPrice) {
        this.closeingPrice = closeingPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getCarPic() {
        return carPic;
    }

    public void setCarPic(List<String> carPic) {
        this.carPic = carPic;
    }

    public String getCarPicString() {
        return carPicString;
    }

    public void setCarPicString(String carPicString) {
        this.carPicString = carPicString;
    }
}
