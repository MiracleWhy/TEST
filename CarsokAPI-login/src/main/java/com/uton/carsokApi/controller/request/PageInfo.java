package com.uton.carsokApi.controller.request;

import javax.validation.constraints.NotNull;

public class PageInfo {
	/** 当前页*/
	@NotNull(message = "curPage must not null")
	private Integer curPage;
	
	/** 一页多少条*/
	@NotNull(message = "pageSize must not null")
	private Integer pageSize;

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
