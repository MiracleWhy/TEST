package com.uton.carsokApi.controller.request;


import java.util.List;

public class FindListRequest {

	private int type;
	private String selects;
	private int completeStatus;
	private int times;
	private String buyStatus;
	private int pageNum;
	private int pageSize;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSelects() {
		return selects;
	}

	public void setSelects(String selects) {
		this.selects = selects;
	}

	public int getCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(int completeStatus) {
		this.completeStatus = completeStatus;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(String buyStatus) {
		this.buyStatus = buyStatus;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
