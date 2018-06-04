package com.uton.carsokApi.controller.request;

/**
 * 子账户登录request
 * 
 * @author bing.cheng
 *
 */
public class SubLoginRequest {

	private String accountKey;
	private String account;
	private String childAccountMobile;
	private String alias;

	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChildAccountMobile() {
		return childAccountMobile;
	}

	public void setChildAccountMobile(String childAccountMobile) {
		this.childAccountMobile = childAccountMobile;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
