package com.uton.carsokApi.controller.response;

import java.util.List;
import java.util.Map;

public class ThirdDataSellCountResponse {
	private List<Map<String,Object>> counts;
	private Long totalCount;

	public List getCounts() {
		return counts;
	}
	public void setCounts(List counts) {
		this.counts = counts;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	

	
}
