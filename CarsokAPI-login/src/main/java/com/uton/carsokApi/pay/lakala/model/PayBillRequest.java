package com.uton.carsokApi.pay.lakala.model;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.PayWayEnum;
import com.uton.carsokApi.model.Acount;

public class PayBillRequest {
	private String orderNo;

	private BigDecimal money;

	private PayWayEnum payWay;

	private Acount acount;

	private String billNo;
	
	private String ip;
	
	private String body;
	
	private String memo;
	
	private String accountRealName;
	
	private JSONObject description;

	public JSONObject getDescription() {
		return description;
	}

	public void setDescription(JSONObject description) {
		this.description = description;
	}

	public String getAccountRealName() {
		return accountRealName;
	}

	public void setAccountRealName(String accountRealName) {
		this.accountRealName = accountRealName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Acount getAcount() {
		return acount;
	}

	public void setAcount(Acount acount) {
		this.acount = acount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public PayWayEnum getPayWay() {
		return payWay;
	}

	public void setPayWay(PayWayEnum payWay) {
		this.payWay = payWay;
	}

}
