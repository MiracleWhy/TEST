package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

public class WxPayRequest {
	/**商品描述*/
	@NotEmpty(message="body is not null")
	private String body;
	
	/**商户订单号*/
	@NotEmpty(message="orderNum is not null")
	private String orderNum;
	
	/**订单总金额*/
	@NotEmpty(message="totalfee is not null")
	private String totalfee;
	
	/**终端ip*/
	private String ip;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	private String appName;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(String totalfee) {
		this.totalfee = totalfee;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
