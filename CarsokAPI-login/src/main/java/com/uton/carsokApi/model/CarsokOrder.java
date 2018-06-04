package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

import com.uton.carsokApi.constants.enums.OrderTypeEnum;

public class CarsokOrder {
	private Integer id;

	private String orderNo;

	private String bizId;

	private BigDecimal totalMoney;

	private BigDecimal payMoney;

	private BigDecimal restMoney;

	private String payStatus;

	private String orderStatus;

	private boolean enable;

	private Date gmtExpire;

	private Date gmtCreate;

	private Date gmtPay;

	private Date gmtModify;

	private OrderTypeEnum type;

	private String memo;

	private String accountId;
	
	private String description;
	
	private String accountRealName;
	
	
	
	public String getAccountRealName() {
		return accountRealName;
	}

	public void setAccountRealName(String accountRealName) {
		this.accountRealName = accountRealName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId == null ? null : bizId.trim();
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public BigDecimal getRestMoney() {
		return restMoney;
	}

	public void setRestMoney(BigDecimal restMoney) {
		this.restMoney = restMoney;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus == null ? null : payStatus.trim();
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus == null ? null : orderStatus.trim();
	}

	public Date getGmtExpire() {
		return gmtExpire;
	}

	public void setGmtExpire(Date gmtExpire) {
		this.gmtExpire = gmtExpire;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtPay() {
		return gmtPay;
	}

	public void setGmtPay(Date gmtPay) {
		this.gmtPay = gmtPay;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}



	public OrderTypeEnum getType() {
		return type;
	}

	public void setType(OrderTypeEnum type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId == null ? null : accountId.trim();
	}
}