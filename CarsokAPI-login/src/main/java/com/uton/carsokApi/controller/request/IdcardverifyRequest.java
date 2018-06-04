package com.uton.carsokApi.controller.request;

/**
 * 身份证实名认证 request
 * 
 * @author bing.cheng
 *
 */
public class IdcardverifyRequest {

	private String idcard;
	private String realName;

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
