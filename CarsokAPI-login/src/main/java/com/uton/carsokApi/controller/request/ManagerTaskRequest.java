package com.uton.carsokApi.controller.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ManagerTaskRequest {
	@NotNull(message="tid is not null")
	private int tid;
	
	@NotEmpty(message="buyprice is not null")
	private String buyprice;
	
	@NotEmpty(message="selfprice is not null")
	private String selfprice;
	
	@NotEmpty(message="overprice is not null")
	private String overprice;
	
	private String remark;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(String buyprice) {
		this.buyprice = buyprice;
	}

	public String getSelfprice() {
		return selfprice;
	}

	public void setSelfprice(String selfprice) {
		this.selfprice = selfprice;
	}

	public String getOverprice() {
		return overprice;
	}

	public void setOverprice(String overprice) {
		this.overprice = overprice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
