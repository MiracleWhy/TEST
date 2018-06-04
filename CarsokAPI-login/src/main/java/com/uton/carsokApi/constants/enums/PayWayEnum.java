package com.uton.carsokApi.constants.enums;

public enum PayWayEnum {
	LAKALA("拉卡拉支付"), 
	
	WECHAT("微信"), 
	
	ALI("支付宝"),
	
	CASH("现金"),
	;
	
	String message;

	PayWayEnum(String message) {
		this.message = message;
	}

	public String message() {
		return this.message;
	}
}
