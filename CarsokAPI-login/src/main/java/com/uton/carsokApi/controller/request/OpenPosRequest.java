package com.uton.carsokApi.controller.request;
/**
 * 开通pos机request
 * @author bing.cheng
 *
 */
public class OpenPosRequest {
	
	private String posLoginPasswd;

	public String getPosLoginPasswd() {
		return posLoginPasswd;
	}

	public void setPosLoginPasswd(String posLoginPasswd) {
		this.posLoginPasswd = posLoginPasswd;
	}
	
}
