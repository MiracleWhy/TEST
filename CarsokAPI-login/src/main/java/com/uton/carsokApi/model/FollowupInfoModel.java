package com.uton.carsokApi.model;

import java.util.Date;

/**
 * 市场管理-跟进信息
 */
public class FollowupInfoModel {
	// 主键id
	private String id;
	// carsok_bussines表id
	private String bussinesId;
	// 跟进信息
	private String followupInfo;
	// 跟进人
	private String followupPerson;
	// 对接人
	private String integrationPerson;
	// 跟进信息类型(
	// FUService-客服;
	// FUConsultant-咨询顾问;
	// FUTrainer-培训师;
	// FUDeputy-副总监;
	// FUDirector-总监)
	private String followupType;
	// 主账号电话
	private String account;
	// 子账号电话
	private String childAccount;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	//地址
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBussinesId() {
		return bussinesId;
	}

	public void setBussinesId(String bussinesId) {
		this.bussinesId = bussinesId;
	}

	public String getFollowupInfo() {
		return followupInfo;
	}

	public void setFollowupInfo(String followupInfo) {
		this.followupInfo = followupInfo;
	}

	public String getFollowupPerson() {
		return followupPerson;
	}

	public void setFollowupPerson(String followupPerson) {
		this.followupPerson = followupPerson;
	}

	public String getIntegrationPerson() {
		return integrationPerson;
	}

	public void setIntegrationPerson(String integrationPerson) {
		this.integrationPerson = integrationPerson;
	}

	public String getFollowupType() {
		return followupType;
	}

	public void setFollowupType(String followupType) {
		this.followupType = followupType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChildAccount() {
		return childAccount;
	}

	public void setChildAccount(String childAccount) {
		this.childAccount = childAccount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}