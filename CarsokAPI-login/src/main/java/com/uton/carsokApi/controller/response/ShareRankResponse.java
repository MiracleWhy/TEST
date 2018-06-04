package com.uton.carsokApi.controller.response;

import java.util.Date;

public class ShareRankResponse {
	private String realName;
	private String count;
	private String shareAccountCode;
	private String accountCode;
	private String account;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getShareAccountCode() {
		return shareAccountCode;
	}

	public void setShareAccountCode(String shareAccountCode) {
		this.shareAccountCode = shareAccountCode;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
