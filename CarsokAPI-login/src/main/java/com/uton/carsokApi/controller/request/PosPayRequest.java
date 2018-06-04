package com.uton.carsokApi.controller.request;

import java.math.BigDecimal;

/**
 * pos机支付
 * @author Administrator
 *
 */
public class PosPayRequest {
	private Integer productId;
	private BigDecimal orderPrice;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	
}
