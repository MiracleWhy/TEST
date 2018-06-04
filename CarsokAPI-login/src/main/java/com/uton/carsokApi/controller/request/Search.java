package com.uton.carsokApi.controller.request;

public class Search {
	private String searchKey;

	private String onShelfStatus;

	private String category;

	private String sort;

	private String maxPrice;

	private String minPrice;

	private String vehicleAge;

	private String proCustomer;

	public String getProCustomer() {
		return proCustomer;
	}

	public void setProCustomer(String proCustomer) {
		this.proCustomer = proCustomer;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getOnShelfStatus() {
		return onShelfStatus;
	}

	public void setOnShelfStatus(String onShelfStatus) {
		this.onShelfStatus = onShelfStatus;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getVehicleAge() {
		return vehicleAge;
	}

	public void setVehicleAge(String vehicleAge) {
		this.vehicleAge = vehicleAge;
	}
}
