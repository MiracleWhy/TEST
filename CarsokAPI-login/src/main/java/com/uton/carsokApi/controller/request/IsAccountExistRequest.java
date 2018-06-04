package com.uton.carsokApi.controller.request;
/**
 * 校验用户是否存在
 * @author bing.cheng
 *
 */
public class IsAccountExistRequest {
	/** 账号 */
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
