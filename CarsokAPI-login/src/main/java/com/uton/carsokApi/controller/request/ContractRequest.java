package com.uton.carsokApi.controller.request;

import javax.validation.constraints.NotNull;

public class ContractRequest {
	@NotNull(message="carid is not null")
	private int carid;
	private int contractType;
	public int getCarid() {
		return carid;
	}
	public void setCarid(int carid) {
		this.carid = carid;
	}
	public int getContractType() {
		return contractType;
	}
	public void setContractType(int contractType) {
		this.contractType = contractType;
	}
	
}
