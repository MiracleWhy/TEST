package com.uton.carsokApi.controller.request;

import java.util.Date;

import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.pay.lakala.model.PayStatus;

public class CarDataSearchRequest extends PageInfo {
	// 模糊搜索
	private String keyword;
	// 开始时间
	private Date carDateStart;
	// 结束时间
	private Date carDateEnd;
	// 排序类型
	private String orderBy;
	// 数据类型
	private String dataType;
	// 品牌
	private String carBrand;
	// 车系
	private String carType;

	private String accountId;

	private String carAge;

	private OrderTypeEnum orderType;

	private PayStatus payStatus;

	private String city;

	private String province;
	
	private String order;
	
	private Integer category;
	
	private String carStatus;
	

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCarAge() {
		return carAge;
	}

	public void setCarAge(String carAge) {
		this.carAge = carAge;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public OrderTypeEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderTypeEnum orderType) {
		this.orderType = orderType;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getCarDateStart() {
		return carDateStart;
	}

	public void setCarDateStart(Date carDateStart) {
		this.carDateStart = carDateStart;
	}

	public Date getCarDateEnd() {
		return carDateEnd;
	}

	public void setCarDateEnd(Date carDateEnd) {
		this.carDateEnd = carDateEnd;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

}
