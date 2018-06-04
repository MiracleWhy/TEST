package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

public class PosPay {
    private Integer id;

    private Integer posId;

    private Integer productId;

    private String payBarCode;

    private BigDecimal amount;

    private BigDecimal orderPrice;

    private Integer receiptType;

    private String payBarCodePicPath;

    private Short status;

    private Date summitTime;

    private Date payTime;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosId() {
        return posId;
    }

    public void setPosId(Integer posId) {
        this.posId = posId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPayBarCode() {
        return payBarCode;
    }

    public void setPayBarCode(String payBarCode) {
        this.payBarCode = payBarCode == null ? null : payBarCode.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(Integer receiptType) {
        this.receiptType = receiptType;
    }

    public String getPayBarCodePicPath() {
        return payBarCodePicPath;
    }

    public void setPayBarCodePicPath(String payBarCodePicPath) {
        this.payBarCodePicPath = payBarCodePicPath == null ? null : payBarCodePicPath.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getSummitTime() {
        return summitTime;
    }

    public void setSummitTime(Date summitTime) {
        this.summitTime = summitTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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
}