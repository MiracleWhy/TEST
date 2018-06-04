package com.uton.carsokApi.controller.response;

public class ShareRankList {
	private String name;
	private String shareCount;
	private String shareDate;
	private String startDay;
	private String endDay;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShareCount() {
		return shareCount;
	}

	public void setShareCount(String shareCoount) {
		this.shareCount = shareCoount;
	}

	public String getShareDate() {
		return shareDate;
	}

	public void setShareDate(String shareDate) {
		this.shareDate = shareDate;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
}