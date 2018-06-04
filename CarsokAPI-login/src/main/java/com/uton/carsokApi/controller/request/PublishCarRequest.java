package com.uton.carsokApi.controller.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 发布车辆请求request
 * 
 * @author bing.cheng
 *
 */
public class PublishCarRequest {
	/** 品牌 */
	@NotEmpty(message="cBrand is not null")
	private String cbrand;
	
	/** 车型 */
	@NotEmpty(message="cModel is not null")
	private String cmodel;
	
	/** 车系 */
	@NotEmpty(message="cType is not null")
	private String ctype;
	
	/** 所在城市 */
	@NotEmpty(message="city is not null")
	private String city;
	
	/** 初次上牌时间 */
	//@NotEmpty(message="firstUpTime is not null")
	private String firstUpTime;
	
	/** 车辆里程 */
	//@NotNull(message="miles is not null")
	private Double miles;
	
	/** 2.4.6版本以前使用图片列表 */
	//@NotNull(message="picList is not null")
	private List<String> picList;

	/** 2.4.6版本使用图片URL列表 */
	//@NotNull(message="picURLList is not null")
	private List<String> picURLList;
	
	/** 销售价(单位:元，精确到分 */
	//@NotEmpty(message="price is not null")
	private String price;
	
	/** 销售价(单位:元，精确到分 */
	//@NotEmpty(message="miniprice is not null")
	private String miniprice;
	
	/** 车辆描述 */
	private String productDescr;
	/** 所在省份 */
	
	@NotEmpty(message="province is not null")
	private String province;
	
	/** 2.4.6以前使用头部主图*/
	//@NotEmpty(message="headerPic is not null")
	private String headerPic;

	/** 2.4.6开始使用头部主图*/
	//@NotEmpty(message="headerPicURL is not null")
	private String headerPicURL;

	/** 任务id */
	private int tid;

	/** 维修信息 */
	private String maintainInfo;

	/** VIN */
	private String vin;

	/**收车价格*/
	private BigDecimal closeingPrice;
	/**
	 * 是否寄售 1:是 2:否
	 */
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFirstUpTime() {
		return firstUpTime;
	}

	public void setFirstUpTime(String firstUpTime) {
		this.firstUpTime = firstUpTime;
	}

	public Double getMiles() {
		return miles;
	}

	public void setMiles(Double miles) {
		this.miles = miles;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductDescr() {
		return productDescr;
	}

	public void setProductDescr(String productDescr) {
		this.productDescr = productDescr;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getHeaderPic() {
		return headerPic;
	}

	public void setHeaderPic(String headerPic) {
		this.headerPic = headerPic;
	}

	public String getMiniprice() {
		return miniprice;
	}

	public void setMiniprice(String miniprice) {
		this.miniprice = miniprice;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getMaintainInfo() {
		return maintainInfo;
	}

	public void setMaintainInfo(String maintainnInfo) {
		this.maintainInfo = maintainnInfo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public List<String> getPicURLList() {return picURLList;}

	public void setPicURLList(List<String> picURLList) {this.picURLList = picURLList;}

	public String getHeaderPicURL() {return headerPicURL;}

	public void setHeaderPicURL(String headerPicURL) {this.headerPicURL = headerPicURL;}
}
