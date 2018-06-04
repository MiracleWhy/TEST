package com.uton.carsokApi.controller.response;

import java.math.BigDecimal;
import java.util.List;

import com.uton.carsokApi.model.PosPayDetail;

public class PosPayResponse {
	private BigDecimal amount; //已收金额
	private Short status;     //支付状态
	private List<PosPayDetail> payDetails;  //支付明细
	private BigDecimal subamount;   //待收金额
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public List<PosPayDetail> getPayDetails() {
		return payDetails;
	}
	public void setPayDetails(List<PosPayDetail> payDetails) {
		this.payDetails = payDetails;
	}
	public BigDecimal getSubamount() {
		return subamount;
	}
	public void setSubamount(BigDecimal subamount) {
		this.subamount = subamount;
	}
	
	
}
