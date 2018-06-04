package com.uton.carsokApi.controller.response;

public class FindListResponse {
	private int tenureCarId;
	private String tenureCarName;
	private String saleTime;
	private String customerName;
	private String customerMobile;
	private String showType;
	private String delayDays;
	private String belongTo;

	public String getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(String delayDays) {
		this.delayDays = delayDays;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public int getTenureCarId() {
		return tenureCarId;
	}

	public void setTenureCarId(int tenureCarId) {
		this.tenureCarId = tenureCarId;
	}

	public String getTenureCarName() {
		return tenureCarName;
	}

	public void setTenureCarName(String tenureCarName) {
		this.tenureCarName = tenureCarName;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
}
