package com.uton.carsokApi.controller.request;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class BaseThirdRequest<T> {
	@NotBlank
	private String partner;
	@NotBlank
	private String token;
	@NotNull
	private T data;
	
	private String provider;
	
	

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
