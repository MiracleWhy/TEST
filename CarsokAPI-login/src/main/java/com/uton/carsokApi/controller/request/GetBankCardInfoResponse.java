package com.uton.carsokApi.controller.request;

/**
 * 银行卡信息返回
 * 
 * @author bing.cheng
 *
 */
public class GetBankCardInfoResponse {

	private String bankNum;
	private String openedBank;
	private String realName;

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getOpenedBank() {
		return openedBank;
	}

	public void setOpenedBank(String openedBank) {
		this.openedBank = openedBank;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
