package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer id;

    private String productNo;

    private String productName;

    private String productDescr;

    private String searchKeyWords;

    private String productShortName;

    private BigDecimal price;
    
    private BigDecimal miniprice;

    private Integer category;

    private Integer accountId;

    private Integer primaryPicId;

    private Short onShelfStatus;

    private Short saleStatus;

    private Short isDel;

    private Date onShelfTime;

    private Date offShelfTime;

    private Date createTime;

    private Date updateTime;

    private String saledPeople;

    private BigDecimal saledPrice;

    private Integer saledType;

    private String maintainInfo;

    private String vin;

    private Integer businessIf;

    private Integer reserveIf;

    private BigDecimal closeingPrice;

    private Integer consignment;

    public Integer getConsignment() {
        return consignment;
    }

    public void setConsignment(Integer consignment) {
        this.consignment = consignment;
    }

    public BigDecimal getCloseingPrice() {
        return closeingPrice;
    }

    public void setCloseingPrice(BigDecimal closeingPrice) {
        this.closeingPrice = closeingPrice;
    }

    public Integer getReserveIf() {
        return reserveIf;
    }

    public void setReserveIf(Integer reserveIf) {
        this.reserveIf = reserveIf;
    }

    public Integer getBusinessIf() {
        return businessIf;
    }

    public void setBusinessIf(Integer businessIf) {
        this.businessIf = businessIf;
    }

    public String getSaledPeople() {
        return saledPeople;
    }

    public void setSaledPeople(String saledPeople) {
        this.saledPeople = saledPeople;
    }

    public BigDecimal getSaledPrice() {
        return saledPrice;
    }

    public void setSaledPrice(BigDecimal saledPrice) {
        this.saledPrice = saledPrice;
    }

    public Integer getSaledType() {
        return saledType;
    }

    public void setSaledType(Integer saledType) {
        this.saledType = saledType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductDescr() {
        return productDescr;
    }

    public void setProductDescr(String productDescr) {
        this.productDescr = productDescr == null ? null : productDescr.trim();
    }

    public String getSearchKeyWords() {
        return searchKeyWords;
    }

    public void setSearchKeyWords(String searchKeyWords) {
        this.searchKeyWords = searchKeyWords == null ? null : searchKeyWords.trim();
    }

    public String getProductShortName() {
        return productShortName;
    }

    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName == null ? null : productShortName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getPrimaryPicId() {
        return primaryPicId;
    }

    public void setPrimaryPicId(Integer primaryPicId) {
        this.primaryPicId = primaryPicId;
    }

    public Short getOnShelfStatus() {
        return onShelfStatus;
    }

    public void setOnShelfStatus(Short onShelfStatus) {
        this.onShelfStatus = onShelfStatus;
    }

    public Short getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Short saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Short getIsDel() {
        return isDel;
    }

    public void setIsDel(Short isDel) {
        this.isDel = isDel;
    }

    public Date getOnShelfTime() {
        return onShelfTime;
    }

    public void setOnShelfTime(Date onShelfTime) {
        this.onShelfTime = onShelfTime;
    }

    public Date getOffShelfTime() {
        return offShelfTime;
    }

    public void setOffShelfTime(Date offShelfTime) {
        this.offShelfTime = offShelfTime;
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

	public BigDecimal getMiniprice() {
		return miniprice;
	}

	public void setMiniprice(BigDecimal miniprice) {
		this.miniprice = miniprice;
	}

    public String getMaintainInfo() {
        return maintainInfo;
    }

    public void setMaintainInfo(String maintainInfo) {
        this.maintainInfo = maintainInfo;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}