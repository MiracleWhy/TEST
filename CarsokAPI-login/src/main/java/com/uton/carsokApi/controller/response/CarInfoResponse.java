package com.uton.carsokApi.controller.response;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 汽车信息详情
 * 
 * @author bing.cheng
 *
 */
public class CarInfoResponse {

	private String firstUpTime;
	private Integer id;
	private String price;
	private String miniprice;
	private List<String> primaryPicUrl;
	private String productName;
	private String miles;
    private String province;
    private String city;
    private Integer oldCarId;
	private String cbrand;
	private String cmodel;
	private String ctype;
	private String productDescr;
	private Integer browseNumTimes;
	private Integer telNumTimes;
	private Integer saledDays;
	private String vin;
	private BigDecimal closeingPrice;
	private Integer consignment;

	public Integer getConsignment() {
		return consignment;
	}

	public void setConsignment(Integer consignment) {
		this.consignment = consignment;
	}

	public BigDecimal getCloseingPrice() {
		return closeingPrice;
	}

	public void setCloseingPrice(BigDecimal closeingPrice) {
		this.closeingPrice = closeingPrice;
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

	public String getMiles() {
		return miles;
	}

	public void setMiles(String miles) {
		this.miles = miles;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getOldCarId() {
		return oldCarId;
	}

	public void setOldCarId(Integer oldCarId) {
		this.oldCarId = oldCarId;
	}

	public String getCbrand() {
		return cbrand;
	}

	public void setCbrand(String cbrand) {
		this.cbrand = cbrand;
	}

	public String getCmodel() {
		return cmodel;
	}

	public void setCmodel(String cmodel) {
		this.cmodel = cmodel;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getProductDescr() {
		return productDescr;
	}

	public void setProductDescr(String productDescr) {
		this.productDescr = productDescr;
	}

	public String getMiniprice() {
		return miniprice;
	}

	public void setMiniprice(String miniprice) {
		this.miniprice = miniprice;
	}

	public Integer getBrowseNumTimes() {
		return browseNumTimes;
	}

	public void setBrowseNumTimes(Integer browseNumTimes) {
		this.browseNumTimes = browseNumTimes;
	}

	public Integer getTelNumTimes() {
		return telNumTimes;
	}

	public void setTelNumTimes(Integer telNumTimes) {
		this.telNumTimes = telNumTimes;
	}

	public Integer getSaledDays() {
		return saledDays;
	}

	public void setSaledDays(Integer saledDays) {
		this.saledDays = saledDays;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
}
