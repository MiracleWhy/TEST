package com.uton.carsokApi.controller.request;

public class PosApplyRequest extends OrderBaseRequest{
	private String accountId;

	private ApplyAccountInfo applyAccountInfo;

	private ApplyInfo applyInfo;

	private String bizId;
	
	private String id;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public ApplyAccountInfo getApplyAccountInfo() {
		return applyAccountInfo;
	}

	public void setApplyAccountInfo(ApplyAccountInfo applyAccountInfo) {
		this.applyAccountInfo = applyAccountInfo;
	}

	public ApplyInfo getApplyInfo() {
		return applyInfo;
	}

	public void setApplyInfo(ApplyInfo applyInfo) {
		this.applyInfo = applyInfo;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

}
