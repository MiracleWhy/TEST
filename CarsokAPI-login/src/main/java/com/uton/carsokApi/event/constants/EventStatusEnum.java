package com.uton.carsokApi.event.constants;

public enum EventStatusEnum {
	WAIT_HANDLE("待处理"),

	WAIT_RETRY("待重试"),

	HANDLE_SUCCESS("处理成功"),

	HANDLE_FAILED("处理失败"),;
	String message;

	private EventStatusEnum(String message) {
		this.message = message;
	}

	String message() {
		return message;
	}
}
