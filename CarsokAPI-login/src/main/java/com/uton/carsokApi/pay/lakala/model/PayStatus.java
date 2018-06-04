package com.uton.carsokApi.pay.lakala.model;

public enum PayStatus {

		WAIT_PAY("未支付"),
	
		PAY_SUCCESS("支付成功"),
	
		CANCEL_PAY("取消支付"),
		
		PAY_FAIL("支付失败")
	;
	String message;

	PayStatus(String message) {
		this.message = message;
	}

	String message() {
		return message;
	}
}
