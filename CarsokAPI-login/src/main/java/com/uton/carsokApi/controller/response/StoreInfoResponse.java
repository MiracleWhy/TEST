package com.uton.carsokApi.controller.response;

/**
 * 店铺信息response
 * 
 * @author bing.cheng
 *
 */
public class StoreInfoResponse {
	private String merchantAddress;
	private String merchantDescr;
	private String merchantImagePath;
	private String merchantName;
	private String mobile;
	private String nick;

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

}
