package com.uton.carsokApi.controller.response;

/**
 * 获取pos机信息responses
 * 
 * @author bing.cheng
 *
 */
public class GetPosInfoResponse {
	
	private Integer accountId;
	private Short applySatus;
	private String createTime;
	private Integer id;
	private Short openStatus;
	private String posLoginAccount;
	private String posSn;
	private String updateTime;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Short getApplySatus() {
		return applySatus;
	}

	public void setApplySatus(Short applySatus) {
		this.applySatus = applySatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(Short openStatus) {
		this.openStatus = openStatus;
	}

	public String getPosLoginAccount() {
		return posLoginAccount;
	}

	public void setPosLoginAccount(String posLoginAccount) {
		this.posLoginAccount = posLoginAccount;
	}

	public String getPosSn() {
		return posSn;
	}

	public void setPosSn(String posSn) {
		this.posSn = posSn;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
