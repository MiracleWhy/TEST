package com.uton.carsokApi.controller.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class PgsTaskRequest {
	@NotNull(message="tid is not null")
	private int tid;
	
	private String infos;

	private String vin;
	
	private String remark;

	private String picOutlook;

	private String picInside;

	private String picPaint;

	public String getPicOutlook() {
		return picOutlook;
	}

	public void setPicOutlook(String picOutlook) {
		this.picOutlook = picOutlook;
	}

	public String getPicInside() {
		return picInside;
	}

	public void setPicInside(String picInside) {
		this.picInside = picInside;
	}

	public String getPicPaint() {
		return picPaint;
	}

	public void setPicPaint(String picPaint) {
		this.picPaint = picPaint;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getInfos() {
		return infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
