package com.uton.carsokApi.pay.lakala.model;

/**
 * 
 * @author jml
 *
 *         2017年3月24日
 */
public enum OrderStatus {

	ALREADY_CONFIRM("订单已确认"),

	ALREADY_CANCLE("订单已取消"),

	ALREDAY_OVER("订单已完结"),

	;
	String message;

	OrderStatus(String message) {
		this.message = message;
	}

	String message() {
		return message;
	}
}
