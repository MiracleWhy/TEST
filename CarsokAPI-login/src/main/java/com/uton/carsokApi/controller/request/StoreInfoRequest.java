package com.uton.carsokApi.controller.request;

/**
 * 更新店铺信息req
 * 
 * @author bing.cheng
 *
 */
public class StoreInfoRequest {
	private String merchantAddress;
	private String merchantDescr;
	private String merchantImagePath;
	private String merchantName;
	private String mobile;
	private String nick;
	private String headportraitpath;
	private String province;
	private String city;
    private String businessLicencePath;
    private String accountKey;
    
	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getMerchantDescr() {
		return merchantDescr;
	}

	public void setMerchantDescr(String merchantDescr) {
		this.merchantDescr = merchantDescr;
	}

	public String getMerchantImagePath() {
		return merchantImagePath;
	}

	public void setMerchantImagePath(String merchantImagePath) {
		this.merchantImagePath = merchantImagePath;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHeadportraitpath() {
		return headportraitpath;
	}

	public void setHeadportraitpath(String headportraitpath) {
		this.headportraitpath = headportraitpath;
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

	public String getBusinessLicencePath() {
		return businessLicencePath;
	}

	public void setBusinessLicencePath(String businessLicencePath) {
		this.businessLicencePath = businessLicencePath;
	}

	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}

}
