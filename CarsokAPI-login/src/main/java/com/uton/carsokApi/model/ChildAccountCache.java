package com.uton.carsokApi.model;

import java.util.List;

public class ChildAccountCache {
    private Integer id;

    private String childAccountMobile;

    private String childAccountName;

    private String accountPhone;
    
    private List<String> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChildAccountMobile() {
        return childAccountMobile;
    }

    public void setChildAccountMobile(String childAccountMobile) {
        this.childAccountMobile = childAccountMobile == null ? null : childAccountMobile.trim();
    }

    public String getChildAccountName() {
        return childAccountName;
    }

    public void setChildAccountName(String childAccountName) {
        this.childAccountName = childAccountName == null ? null : childAccountName.trim();
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone == null ? null : accountPhone.trim();
    }

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
    
}