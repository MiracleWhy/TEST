package com.uton.carsokApi.controller.request;

import javax.validation.constraints.NotNull;


public class AccountAuthRequest {
	/** 账户检测类型 */
	@NotNull(message="status is not null")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
