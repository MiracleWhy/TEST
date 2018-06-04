package com.uton.carsokApi.controller.request;

/**
 * 验证码校验
 * 
 * @author bing.cheng
 *
 */
public class CheckCodeRequest {
	private String account;
	private String code;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
