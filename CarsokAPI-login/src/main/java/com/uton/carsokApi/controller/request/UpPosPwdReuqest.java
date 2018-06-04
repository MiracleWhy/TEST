package com.uton.carsokApi.controller.request;
/**
 * 更新密码
 * @author bing.cheng
 *
 */
public class UpPosPwdReuqest {

	private String  newPosLoginPasswd;			
	private String  posLoginPasswd;
	public String getNewPosLoginPasswd() {
		return newPosLoginPasswd;
	}
	public void setNewPosLoginPasswd(String newPosLoginPasswd) {
		this.newPosLoginPasswd = newPosLoginPasswd;
	}
	public String getPosLoginPasswd() {
		return posLoginPasswd;
	}
	public void setPosLoginPasswd(String posLoginPasswd) {
		this.posLoginPasswd = posLoginPasswd;
	}
	
	
	
}
