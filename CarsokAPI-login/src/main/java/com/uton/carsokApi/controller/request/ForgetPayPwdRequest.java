package com.uton.carsokApi.controller.request;
/**
 * 忘记支付密码
 * @author bing.cheng
 *
 */
public class ForgetPayPwdRequest {

	private String code;
	private String  payPassword;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	
	
}
