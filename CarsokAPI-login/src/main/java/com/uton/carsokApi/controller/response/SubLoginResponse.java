package com.uton.carsokApi.controller.response;

import java.util.List;

import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.ChildAccountCache;

/**
 * 子账户登录返回
 * 
 * @author bing.cheng
 *
 */
public class SubLoginResponse {

	private String subToken;
	private ChildAccount childAccount;
	private List<String> roles;

	public String getSubToken() {
		return subToken;
	}

	public void setSubToken(String subToken) {
		this.subToken = subToken;
	}

	public ChildAccount getChildAccount() {
		return childAccount;
	}

	public void setChildAccount(ChildAccount childAccount) {
		this.childAccount = childAccount;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
