package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

public class QuerySubUserOnlyChkRequest {
	@NotEmpty(message="mobile is not null")
		private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
