package com.uton.carsokApi.controller.request;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.uton.carsokApi.pay.lakala.model.OrderStatus;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
@JsonDeserialize
public class ThirdDataSellCountRequest {
	private String provider;
	@JSONField (format="yyyy-MM-dd")
	private Date gmtStart;
	@JSONField (format="yyyy-MM-dd")
	private Date gmtEnd;
	private String  type;
	private PayStatus payStatus;
	private OrderStatus orderStatus;
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public PayStatus getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public Date getGmtStart() {
		return gmtStart;
	}
	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}
	public Date getGmtEnd() {
		return gmtEnd;
	}
	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}
	
	
}
