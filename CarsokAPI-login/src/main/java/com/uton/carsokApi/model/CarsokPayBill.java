package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

import com.uton.carsokApi.constants.enums.PayWayEnum;

public class CarsokPayBill {
	private Integer id;

	private String orderNo;

	private String billNo;

	private BigDecimal billMoney;

	private String billStatus;

	private Date gmtModify;

	private Date gmtPay;

	private boolean enable;

	private String memo;

	private BigDecimal billCommission;
	
	private String accountId;
	
	private String accountRealName;
	
	private BigDecimal payMoney;
	
	private String body;
	
	private PayWayEnum payWay;
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public PayWayEnum getPayWay() {
		return payWay;
	}

	public void setPayWay(PayWayEnum payWay) {
		this.payWay = payWay;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountRealName() {
		return accountRealName;
	}

	public void setAccountRealName(String accountRealName) {
		this.accountRealName = accountRealName;
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

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo == null ? null : billNo.trim();
	}

	public BigDecimal getBillMoney() {
		return billMoney;
	}

	public void setBillMoney(BigDecimal billMoney) {
		this.billMoney = billMoney;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus == null ? null : billStatus.trim();
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Date getGmtPay() {
		return gmtPay;
	}

	public void setGmtPay(Date gmtPay) {
		this.gmtPay = gmtPay;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public BigDecimal getBillCommission() {
		return billCommission;
	}

	public void setBillCommission(BigDecimal billCommission) {
		this.billCommission = billCommission;
	}
}