package com.uton.carsokApi.controller.response;

import java.math.BigDecimal;
import java.util.List;

import com.uton.carsokApi.model.TaskZbBill;
import com.uton.carsokApi.model.ZbMoneyInfo;

public class ZbyTaskInfoResponse {
	private String zbMoney;
	private String vin;
	private List<ZbMoneyInfo> mlist;
	private BigDecimal closingPrice;
	private List<TaskZbBill> billList;

	public List<TaskZbBill> getBillList() {
		return billList;
	}

	public void setBillList(List<TaskZbBill> billList) {
		this.billList = billList;
	}

	public BigDecimal getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(BigDecimal closingPrice) {
		this.closingPrice = closingPrice;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getZbMoney() {
		return zbMoney;
	}
	public void setZbMoney(String zbMoney) {
		this.zbMoney = zbMoney;
	}
	public List<ZbMoneyInfo> getMlist() {
		return mlist;
	}
	public void setMlist(List<ZbMoneyInfo> mlist) {
		this.mlist = mlist;
	}
	
}
