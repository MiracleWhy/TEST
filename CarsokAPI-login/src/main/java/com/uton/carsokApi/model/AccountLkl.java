package com.uton.carsokApi.model;

import java.util.Date;

import com.uton.carsokApi.pay.lakala.model.LaKaLaApplyStatusEnum;

public class AccountLkl {
	private Integer id;

	private String accountId;

	private String realName;

	private String merName;

	private String address;

	private String areaName;

	private Date gmtCreate;

	private String accountNo;

	private String accountBankName;

	private LaKaLaApplyStatusEnum applyStatus;

	private String openStatus;

	private Date gmtModify;

	private String memo;

	private Date gmtOpen;
	
	private String applyImage;

	public String getApplyImage() {
		return applyImage;
	}

	public void setApplyImage(String applyImage) {
		this.applyImage = applyImage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId == null ? null : accountId.trim();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName == null ? null : merName.trim();
	}

	public LaKaLaApplyStatusEnum getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(LaKaLaApplyStatusEnum applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName == null ? null : areaName.trim();
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo == null ? null : accountNo.trim();
	}

	public String getAccountBankName() {
		return accountBankName;
	}

	public void setAccountBankName(String accountBankName) {
		this.accountBankName = accountBankName == null ? null : accountBankName.trim();
	}

	public String getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus == null ? null : openStatus.trim();
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public Date getGmtOpen() {
		return gmtOpen;
	}

	public void setGmtOpen(Date gmtOpen) {
		this.gmtOpen = gmtOpen;
	}
	public String applyStatusName(){
		return applyStatus.message();
	}
}