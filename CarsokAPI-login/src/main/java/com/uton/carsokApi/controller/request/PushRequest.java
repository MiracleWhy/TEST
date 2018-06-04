package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

public class PushRequest {
	@NotEmpty(message="mobile is not null")
	private String mobile;
	@NotEmpty(message="content is not null")
	private String content;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
