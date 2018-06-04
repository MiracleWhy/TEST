package com.uton.carsokApi.controller.response;

/**
 * 子账户列表response
 * 
 * @author bing.cheng
 *
 */
public class SubUserListResponse {

	private String childAccountMobile;
	private String childAccountName;
	private Integer id;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

}
