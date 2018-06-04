package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

public class ZbMoneyInfo {
	private int id;
	private int zbyId;
	private String detail;
	private String classification;
	private BigDecimal amount;
	private Integer againTimes;
	private Date againTime;

	public Date getAgainTime() {
		return againTime;
	}

	public void setAgainTime(Date againTime) {
		this.againTime = againTime;
	}

	public Integer getAgainTimes() {
		return againTimes;
	}

	public void setAgainTimes(Integer againTimes) {
		this.againTimes = againTimes;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getZbyId() {
		return zbyId;
	}
	public void setZbyId(int zbyId) {
		this.zbyId = zbyId;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getClassification() { return classification; }
	public void setClassification(String classification) { this.classification = classification; }
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
