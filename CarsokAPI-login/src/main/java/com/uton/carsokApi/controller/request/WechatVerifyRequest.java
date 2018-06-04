package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotBlank;

public class WechatVerifyRequest {
	@NotBlank(message = "openId不能为空")
	private String openId;
	@NotBlank(message = "refreshToken不能为空")
	private String refreshToken;

	private String wxNickName;
	
	private String wxHeadUrlString;

	public String getWxNickName() {
		return wxNickName;
	}

	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}

	public String getWxHeadUrlString() {
		return wxHeadUrlString;
	}

	public void setWxHeadUrlString(String wxHeadUrlString) {
		this.wxHeadUrlString = wxHeadUrlString;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
