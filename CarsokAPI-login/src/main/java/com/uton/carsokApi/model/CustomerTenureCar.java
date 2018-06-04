package com.uton.carsokApi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by SEELE on 2017/12/6.
 */
public class CustomerTenureCar implements Serializable{
    private Integer id;
    private String tenureCarname;
    private String tenureVin;
    private String tenureCarnum;
    private String tenureCartype;
    private Date tenureCompulsory;
    private Date tenureBusiness;
    private Date tenureMaintain;
    private Date tenureWarranty;
    private Double tenureCarprice;
    private Integer productId;
    private Date createTime;
    private Date updateTime;
    private Integer enable;
    private String salePeople;
    private String carMiles;
    private Date saleTime;
    private Integer accountId;
    private Integer childId;
    private String brand;
    private Integer threeStatus;
    private Integer sevenStatus;
    private Integer customerId;
    private String purchaseStatus;
    private String isDrivingTest;
    private String isBussiness;
    private Integer isNewRecord;
    private String remark;
    private Integer isDone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenureCarname() {
        return tenureCarname;
    }

    public void setTenureCarname(String tenureCarname) {
        this.tenureCarname = tenureCarname;
    }

    public String getTenureVin() {
        return tenureVin;
    }

    public void setTenureVin(String tenureVin) {
        this.tenureVin = tenureVin;
    }

    public String getTenureCarnum() {
        return tenureCarnum;
    }

    public void setTenureCarnum(String tenureCarnum) {
        this.tenureCarnum = tenureCarnum;
    }

    public String getTenureCartype() {
        return tenureCartype;
    }

    public void setTenureCartype(String tenureCartype) {
        this.tenureCartype = tenureCartype;
    }

    public Date getTenureCompulsory() {
        return tenureCompulsory;
    }

    public void setTenureCompulsory(Date tenureCompulsory) {
        this.tenureCompulsory = tenureCompulsory;
    }

    public Date getTenureBusiness() {
        return tenureBusiness;
    }

    public void setTenureBusiness(Date tenureBusiness) {
        this.tenureBusiness = tenureBusiness;
    }

    public Date getTenureMaintain() {
        return tenureMaintain;
    }

    public void setTenureMaintain(Date tenureMaintain) {
        this.tenureMaintain = tenureMaintain;
    }

    public Date getTenureWarranty() {
        return tenureWarranty;
    }

    public void setTenureWarranty(Date tenureWarranty) {
        this.tenureWarranty = tenureWarranty;
    }

    public Double getTenureCarprice() {
        return tenureCarprice;
    }

    public void setTenureCarprice(Double tenureCarprice) {
        this.tenureCarprice = tenureCarprice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getSalePeople() {
        return salePeople;
    }

    public void setSalePeople(String salePeople) {
        this.salePeople = salePeople;
    }

    public String getCarMiles() {
        return carMiles;
    }

    public void setCarMiles(String carMiles) {
        this.carMiles = carMiles;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getThreeStatus() {
        return threeStatus;
    }

    public void setThreeStatus(Integer threeStatus) {
        this.threeStatus = threeStatus;
    }

    public Integer getSevenStatus() {
        return sevenStatus;
    }

    public void setSevenStatus(Integer sevenStatus) {
        this.sevenStatus = sevenStatus;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public String getIsDrivingTest() {
        return isDrivingTest;
    }

    public void setIsDrivingTest(String isDrivingTest) {
        this.isDrivingTest = isDrivingTest;
    }

    public String getIsBussiness() {
        return isBussiness;
    }

    public void setIsBussiness(String isBussiness) {
        this.isBussiness = isBussiness;
    }

    public Integer getIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(Integer isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDone() {
        return isDone;
    }

    public void setIsDone(Integer isDone) {
        this.isDone = isDone;
    }
}
