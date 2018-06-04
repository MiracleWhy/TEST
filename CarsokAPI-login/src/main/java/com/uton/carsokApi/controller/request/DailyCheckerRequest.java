package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public class DailyCheckerRequest {
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<String> getCheckerList() {
		return checkerList;
	}

	public void setCheckerList(List<String> checkerList) {
		this.checkerList = checkerList;
	}

	private String accountId;
	private List<String> checkerList;


}
