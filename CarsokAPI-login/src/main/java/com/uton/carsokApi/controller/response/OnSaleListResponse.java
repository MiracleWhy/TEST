package com.uton.carsokApi.controller.response;

import java.util.Date;
import java.util.List;

/**
 * 在售列表返回
 * 
 * @author bing.cheng
 *
 */
public class OnSaleListResponse {
	private Integer browseNumTimes;
	private String firstUpTime;
	private Integer id;
	private String price;
	private String miniprice;
	private List<String> primaryPicUrl;
	private String productName;
	private Integer telNumTimes;
	/** 在售天数*/
	private Integer saledDays;
	private String miles;
	private List<String> sharePics;
	private String mobile;
	private String merchantName;
	private String producturl; 
	private String desc;
	private Date onShelfTime;
	private String vin;
	private int status;
	private String  orderNo;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getOnShelfTime() {
		return onShelfTime;
	}

	public void setOnShelfTime(Date onShelfTime) {
		this.onShelfTime = onShelfTime;
	}

	public Integer getBrowseNumTimes() {
		return browseNumTimes;
	}

	public void setBrowseNumTimes(Integer browseNumTimes) {
		this.browseNumTimes = browseNumTimes;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getTelNumTimes() {
		return telNumTimes;
	}

	public void setTelNumTimes(Integer telNumTimes) {
		this.telNumTimes = telNumTimes;
	}

	public List<String> getPrimaryPicUrl() {
		return primaryPicUrl;
	}

	public void setPrimaryPicUrl(List<String> primaryPicUrl) {
		this.primaryPicUrl = primaryPicUrl;
	}

	public Integer getSaledDays() {
		return saledDays;
	}

	public void setSaledDays(Integer saledDays) {
		this.saledDays = saledDays;
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

	public List<String> getSharePics() {
		return sharePics;
	}

	public void setSharePics(List<String> sharePics) {
		this.sharePics = sharePics;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProducturl() {
		return producturl;
	}

	public void setProducturl(String producturl) {
		this.producturl = producturl;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
}
