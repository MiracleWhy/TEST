package com.uton.carsokApi.controller.response;

import java.util.Date;
import java.util.List;

/**
 * 已售列表res
 * 
 * @author bing.cheng
 *
 */
public class SaledListResponse {

	private String firstUpTime;
	private Integer id;
	private String price;
	private String miniprice;
	private List<String> primaryPicUrl;
	private String productName;
	private String miles;
	private Date onShelfTime;
	private String vin;
	private String orderNo;
	private String orderType;
	private Integer consignment;
	private String payStatus;
	
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getConsignment() {
		return consignment;
	}

	public void setConsignment(Integer consignment) {
		this.consignment = consignment;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getOnShelfTime() {
		return onShelfTime;
	}

	public void setOnShelfTime(Date onShelfTime) {
		this.onShelfTime = onShelfTime;
	}

	public String getFirstUpTime() {
		return firstUpTime;
	}

	public void setFirstUpTime(String firstUpTime) {
		this.firstUpTime = firstUpTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<String> getPrimaryPicUrl() {
		return primaryPicUrl;
	}

	public void setPrimaryPicUrl(List<String> primaryPicUrl) {
		this.primaryPicUrl = primaryPicUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMiniprice() {
		return miniprice;
	}

	public void setMiniprice(String miniprice) {
		this.miniprice = miniprice;
	}

	public String getMiles() {
		return miles;
	}

	public void setMiles(String miles) {
		this.miles = miles;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
}
