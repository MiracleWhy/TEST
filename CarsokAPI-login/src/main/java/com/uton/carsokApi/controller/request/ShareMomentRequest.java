package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 朋友圈分享计数
 */
public class ShareMomentRequest {
	@NotEmpty(message="mobile is not null")
	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
