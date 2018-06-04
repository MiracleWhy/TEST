package com.uton.carsokApi.controller.response;

/**
 * 提现列表response
 * 
 * @author bing.cheng
 *
 */
public class CashListResponse {

	private String amount;
	private String createTime;
	private Short status;
	private String withdrawNum;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getWithdrawNum() {
		return withdrawNum;
	}

	public void setWithdrawNum(String withdrawNum) {
		this.withdrawNum = withdrawNum;
	}

}
