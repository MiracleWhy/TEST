package com.uton.carsokApi.model;

import java.util.Date;

public class TenureCarFollow {
	private Integer id;
	private Integer tenurecarId;
	private String followMessage;
	private Integer followType;
	private Integer accountId;
	private Integer childId;
	private Date createTime;
	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTenurecarId() {
		return tenurecarId;
	}

	public void setTenurecarId(Integer tenurecarId) {
		this.tenurecarId = tenurecarId;
	}

	public String getFollowMessage() {
		return followMessage;
	}

	public void setFollowMessage(String followMessage) {
		this.followMessage = followMessage;
	}

	public Integer getFollowType() {
		return followType;
	}

	public void setFollowType(Integer followType) {
		this.followType = followType;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}