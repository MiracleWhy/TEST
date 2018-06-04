package com.uton.carsokApi.controller.request;
/**
 * 登錄參數
 * @author bing.cheng
 *
 */
public class LoginRequest {
	/** 账号 */
	private String account;
	/** 账号密码 */
	private String accountPassword;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

}
