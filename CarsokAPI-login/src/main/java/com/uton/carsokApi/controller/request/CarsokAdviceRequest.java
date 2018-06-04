package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

public class CarsokAdviceRequest {

	@NotEmpty(message = "accountCode is not Null")
	private String accountCode;

	@NotEmpty(message = "content is not Null")
	private String content;

//	@NotEmpty(message = "contactInfo is not Null")
	private String contactInfo;

	private String solution;

	private String adviceType;

	private Date createTime;

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getAdviceType() {
		return adviceType;
	}

	public void setAdviceType(String adviceType) {
		this.adviceType = adviceType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
