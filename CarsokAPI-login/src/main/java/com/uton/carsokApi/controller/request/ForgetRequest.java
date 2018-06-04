package com.uton.carsokApi.controller.request;
/**
 * 找回密码
 * @author bing.cheng
 *
 */
public class ForgetRequest {
	/** 账号 */
	private String account;

	private String passWord;
	
	private String code;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
