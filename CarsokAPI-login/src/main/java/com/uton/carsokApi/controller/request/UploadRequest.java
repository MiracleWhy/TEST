package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

public class UploadRequest {
	
	private String imgstr;

	private String imgstrURL;

	public String getImgstr() {
		return imgstr;
	}

	public void setImgstr(String imgstr) {
		this.imgstr = imgstr;
	}

	public String getImgstrURL() {return imgstrURL;}

	public void setImgstrURL(String imgstrURL) {this.imgstrURL = imgstrURL;}
}
