package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotBlank;

public class CarsokAppVersion {

	@NotBlank(message = "distinction is not Null")
	private String distinction;

	public String getDistinction() {
		return distinction;
	}

	public void setDistinction(String distinction) {
		this.distinction = distinction;
	}
}
