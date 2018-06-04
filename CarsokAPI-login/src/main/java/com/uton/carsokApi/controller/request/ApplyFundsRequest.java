package com.uton.carsokApi.controller.request;
/**
 * 申请配资request
 * @author bing.cheng
 *
 */
public class ApplyFundsRequest {
	private Integer productId;
	private String amount;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
}
