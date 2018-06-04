package com.uton.carsokApi.constants.enums;

public enum OrderTypeEnum {
	CAR("车辆购买"),

	DATA("车源数据购买"),
	
	TEST("测试订单"),
	
	POS_APPLY("POS申请"),
	
	OTHER("其他类型订单"),
	;
	String message;

	OrderTypeEnum(String message) {
		this.message = message;
	}

	public String message() {
		return this.message;
	}
}
