package com.uton.carsokApi.controller.request;
/**
 * 注册request
 * @author bing.cheng
 *
 */
public class RegisterRequest {

	/** 账号密码，服务器account做盐值，md5 32存储*/
	private String accountPassword;
	
	/** 账户类型：1二手车；2新车；3服务*/
	private String accountType;
	
	/** 账号 手机号码*/
	private String account;
	
	/** 验证码*/
	private String code;

	/**
	 * 邀请人电话号
	 * @return
	 */
	private String inviter;

	public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
