package com.uton.carsokApi.controller.request;
/**
 * 修改支付密码
 * @author bing.cheng
 *
 */
public class UpPayPwdRequest {

	private String nwPayPassword;	
	private String  payPassword;
	public String getNwPayPassword() {
		return nwPayPassword;
	}
	public void setNwPayPassword(String nwPayPassword) {
		this.nwPayPassword = nwPayPassword;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	
	
	
}
