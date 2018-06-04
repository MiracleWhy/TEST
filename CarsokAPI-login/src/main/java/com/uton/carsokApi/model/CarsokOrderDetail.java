package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

public class CarsokOrderDetail {
    private Integer id;

    private String productId;

    private BigDecimal productPrice;

    private Integer productCount;

    private String carsokOrderDetailcol;

    private String orderNo;

    private String status;

    private String memo;

    private Date gmtModify;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getCarsokOrderDetailcol() {
        return carsokOrderDetailcol;
    }

    public void setCarsokOrderDetailcol(String carsokOrderDetailcol) {
        this.carsokOrderDetailcol = carsokOrderDetailcol == null ? null : carsokOrderDetailcol.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}