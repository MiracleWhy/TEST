package com.uton.carsokApi.controller.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/19 0019.
 */
public class TenureCarMsgRequest {
    private Integer tid;
    private Integer tcid;
    private String custName;
    private String custPhone;
    private String custSex;
    private String custAge;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date custBirthday;
    private String custThreevisit;
    private String custSevenvisit;
    private String custVocation;
    private String custBeforecar;
    private String custRemark;
    private String custsaledpeople;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date custVisittime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    private Integer tenable;
    private Integer accountId;
    private Integer childId;
    private Integer tenurecarId;
    private String tenureCarname;
    private String tenureVin;
    private String tenureCarnum;
    private Integer tenureCartype;
    private BigDecimal tenureCarprice;
    private String carMales;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date tenureCompulsory;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date tenureBusiness;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date tenureMaintain;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date tenureWarranty;
    private Integer productId;
    private Integer tcenable;
    private String salePeople;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date saleTime;
    private Integer purchaseStatus;

    public String getCarMales() {
        return carMales;
    }

    public void setCarMales(String carMales) {
        this.carMales = carMales;
    }

    public Integer getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(Integer purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Integer getTenurecarId() {
        return tenurecarId;
    }

    public void setTenurecarId(Integer tenurecarId) {
        this.tenurecarId = tenurecarId;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getTcid() {
        return tcid;
    }

    public void setTcid(Integer tcid) {
        this.tcid = tcid;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    public String getCustAge() {
        return custAge;
    }

    public void setCustAge(String custAge) {
        this.custAge = custAge;
    }

    public Date getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(Date custBirthday) {
        this.custBirthday = custBirthday;
    }

    public String getCustThreevisit() {
        return custThreevisit;
    }

    public void setCustThreevisit(String custThreevisit) {
        this.custThreevisit = custThreevisit;
    }

    public String getCustSevenvisit() {
        return custSevenvisit;
    }

    public void setCustSevenvisit(String custSevenvisit) {
        this.custSevenvisit = custSevenvisit;
    }

    public String getCustVocation() {
        return custVocation;
    }

    public void setCustVocation(String custVocation) {
        this.custVocation = custVocation;
    }

    public String getCustBeforecar() {
        return custBeforecar;
    }

    public void setCustBeforecar(String custBeforecar) {
        this.custBeforecar = custBeforecar;
    }

    public String getCustRemark() {
        return custRemark;
    }

    public void setCustRemark(String custRemark) {
        this.custRemark = custRemark;
    }

    public String getCustsaledpeople() {
        return custsaledpeople;
    }

    public void setCustsaledpeople(String custsaledpeople) {
        this.custsaledpeople = custsaledpeople;
    }

    public Date getCustVisittime() {
        return custVisittime;
    }

    public void setCustVisittime(Date custVisittime) {
        this.custVisittime = custVisittime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTenable() {
        return tenable;
    }

    public void setTenable(Integer tenable) {
        this.tenable = tenable;
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

    public Integer getTcenable() {
        return tcenable;
    }

    public void setTcenable(Integer tcenable) {
        this.tcenable = tcenable;
    }

    public String getSalePeople() {
        return salePeople;
    }

    public void setSalePeople(String salePeople) {
        this.salePeople = salePeople;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }
}
