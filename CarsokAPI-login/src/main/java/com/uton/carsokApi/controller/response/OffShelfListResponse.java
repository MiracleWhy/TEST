package com.uton.carsokApi.controller.response;

import java.util.Date;
import java.util.List;

/**
 * 未上架列表res
 * 
 * @author bing.cheng
 *
 */
public class OffShelfListResponse {

	private String firstUpTime;
	private Integer id;
	private String price;
	private String miniprice;
	private List<String> primaryPicUrl;
	private String productName;
	private Short status;
	private String miles;
	private Date onShelfTime;
	private String vin;
	private Integer consignment;

	public Integer getConsignment() {
		return consignment;
	}

	public void setConsignment(Integer consignment) {
		this.consignment = consignment;
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
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
