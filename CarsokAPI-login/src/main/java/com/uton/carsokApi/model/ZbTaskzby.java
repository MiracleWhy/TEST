package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

public class ZbTaskzby {
	private int id;
	private int tid;
	private BigDecimal zbMoney;
	private String vin;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	private String remark;
	private Integer zbyAgainTimes;
	private Date zbyAgainTime;

	public Date getZbyAgainTime() {
		return zbyAgainTime;
	}

	public void setZbyAgainTime(Date zbyAgainTime) {
		this.zbyAgainTime = zbyAgainTime;
	}

	public Integer getZbyAgainTimes() {
		return zbyAgainTimes;
	}

	public void setZbyAgainTimes(Integer zbyAgainTimes) {
		this.zbyAgainTimes = zbyAgainTimes;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public BigDecimal getZbMoney() {
		return zbMoney;
	}
	public void setZbMoney(BigDecimal zbMoney) {
		this.zbMoney = zbMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
