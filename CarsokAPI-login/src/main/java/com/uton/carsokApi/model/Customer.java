package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class Customer {
    private Integer id;
    private Integer accountId;
    private Integer childId;
    private Date inTime;
    private Date outTime;
    private Integer peopleNum;
    private String salesAdviser;
    private String customerName;
    private String customerPhone;
    private Integer customerStatus;
    private String informationSources;
    private String IntentionalVehicle;
    private String customerLevel;
    private String customerTrack;
    private String mobiles;
    private String customerBudget;
    private String customerRegion;
    private Integer customerCome;
    private String brand;
    private java.sql.Date updateTime;


    public java.sql.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.sql.Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getCustomerCome() {
        return customerCome;
    }

    public void setCustomerCome(Integer customerCome) {
        this.customerCome = customerCome;
    }

    public String getCustomerBudget() {
        return customerBudget;
    }

    public void setCustomerBudget(String customerBudget) {
        this.customerBudget = customerBudget;
    }

    public String getCustomerRegion() {
        return customerRegion;
    }

    public void setCustomerRegion(String customerRegion) {
        this.customerRegion = customerRegion;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getCustomerTrack() {
        return customerTrack;
    }

    public void setCustomerTrack(String customerTrack) {
        this.customerTrack = customerTrack;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getSalesAdviser() {
        return salesAdviser;
    }

    public void setSalesAdviser(String salesAdviser) {
        this.salesAdviser = salesAdviser;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(Integer customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getInformationSources() {
        return informationSources;
    }

    public void setInformationSources(String informationSources) {
        this.informationSources = informationSources;
    }

    public String getIntentionalVehicle() {
        return IntentionalVehicle;
    }

    public void setIntentionalVehicle(String intentionalVehicle) {
        IntentionalVehicle = intentionalVehicle;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
    }
}
