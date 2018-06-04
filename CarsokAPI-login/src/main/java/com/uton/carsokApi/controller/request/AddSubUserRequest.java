package com.uton.carsokApi.controller.request;

/**
 * 新增子账户request
 */
public class AddSubUserRequest {

	private String childAccountMobile;
	private String childAccountName;

	public String getChildAccountMobile() {
		return childAccountMobile;
	}

	public void setChildAccountMobile(String childAccountMobile) {
		this.childAccountMobile = childAccountMobile;
	}

	public String getChildAccountName() {
		return childAccountName;
	}

	public void setChildAccountName(String childAccountName) {
		this.childAccountName = childAccountName;
	}

}
