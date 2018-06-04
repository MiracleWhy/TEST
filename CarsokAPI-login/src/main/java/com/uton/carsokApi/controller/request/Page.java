package com.uton.carsokApi.controller.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 分页查询
 * @author bing.cheng
 *
 */
public class Page {
	/** 当前页*/
	@NotNull(message = "curPage must not null")
	private Integer curPage;
	
	/** 一页多少条*/
	@NotNull(message = "pageSize must not null")
	private Integer pageSize;

	/** 车源(SearchCondition) */
	private String category;

	/** 车龄(SearchCondition) */
	private String vehicleAge;

	/** 排序方式(SearchCondition) */
	private String sort;

	/** 金额最大(SearchCondition) */
	private BigDecimal maxPrice;

	/** 金额最大(SearchCondition) */
	private BigDecimal minPrice;

	//作为用来判定是否为潜客购车调用接口的标识符
	private String proCustomer;

	public String getProCustomer() {
		return proCustomer;
	}

	public void setProCustomer(String proCustomer) {
		this.proCustomer = proCustomer;
	}

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVehicleAge() {
		return vehicleAge;
	}

	public void setVehicleAge(String vehicleAge) {
		this.vehicleAge = vehicleAge;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
}
