package com.uton.carsokApi.model;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
public class CustomerCar {
    private Integer id;
    private String tenureCarname;
    private String tenureVin;
    private String tenureCarnum;
    private Integer tenureCartype;
    private Date tenureCompulsory;
    private Date tenureBusiness;
    private Date tenureMaintain;
    private Date tenureWarranty;
    private BigDecimal tenureCarprice;
    private Integer productId;
    private Date createTime;
    private Date updateTime;
    private Integer enable;
    private String salePeople;
    private String carMales;
    private Date saleTime;
    private Integer accountId;
    private Integer childId;
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public String getCarMales() {
        return carMales;
    }

    public void setCarMales(String carMales) {
        this.carMales = carMales;
    }

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

    public Integer getTenureCartype() {
        return tenureCartype;
    }

    public void setTenureCartype(Integer tenureCartype) {
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

    public BigDecimal getTenureCarprice() {
        return tenureCarprice;
    }

    public void setTenureCarprice(BigDecimal tenureCarprice) {
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
}
