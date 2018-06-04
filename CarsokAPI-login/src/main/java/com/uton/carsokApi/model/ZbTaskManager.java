package com.uton.carsokApi.model;

import java.math.BigDecimal;

public class ZbTaskManager {
	private int id;
	private int tid;
	private BigDecimal buyprice;
	private BigDecimal selfprice;
	private BigDecimal overprice;
	private String remark;
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
	public BigDecimal getBuyprice() {
		return buyprice;
	}
	public void setBuyprice(BigDecimal buyprice) {
		this.buyprice = buyprice;
	}
	public BigDecimal getSelfprice() {
		return selfprice;
	}
	public void setSelfprice(BigDecimal selfprice) {
		this.selfprice = selfprice;
	}
	public BigDecimal getOverprice() {
		return overprice;
	}
	public void setOverprice(BigDecimal overprice) {
		this.overprice = overprice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
