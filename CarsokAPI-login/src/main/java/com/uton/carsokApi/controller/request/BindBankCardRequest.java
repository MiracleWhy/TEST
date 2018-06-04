package com.uton.carsokApi.controller.request;

/**
 * 绑定银行卡request
 * 
 * @author bing.cheng
 *
 */
public class BindBankCardRequest {
	
	private String bankcard;	
	
	private String openedBank;
	
	public String getBankcard() {
		return bankcard;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	public String getOpenedBank() {
		return openedBank;
	}
	public void setOpenedBank(String openedBank) {
		this.openedBank = openedBank;
	}
	
}
