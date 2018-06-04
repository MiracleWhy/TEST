package com.uton.carsokApi.model;

public class ZbTaskpgs {
	private int id;
	private int tid;
	private String infos;
	private String vin;
	private String remark;
	private String picOutlook;	//外观图片url
	private String picInside;	//内饰图片url
	private String picPaint;	//漆面图片url

	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
}
