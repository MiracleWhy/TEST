package com.uton.carsokApi.controller.request;


import org.hibernate.validator.constraints.NotEmpty;

public class PublishTaskRequest {

	private  int acquisitioncarId;

	private String carName;
	
	private String carNum;
	
	private int infoSource;
	
	private String infoName;
	
	private String infoMobile;
	
	private String arcPath;
	
	private String dlPath;
	
	private String policyPath;
	
	private String idcardPath;

	private String arcURLPath;

	private String dlURLPath;

	private String policyURLPath;

	private String idcardURLPath;
	
	private String remark;
	
	private int keysNum;
	
	private String selfName;
	
	private String selfMobile;

	private String mobile;

	private String vin;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public int getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(int infoSource) {
		this.infoSource = infoSource;
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public String getInfoMobile() {
		return infoMobile;
	}

	public void setInfoMobile(String infoMobile) {
		this.infoMobile = infoMobile;
	}

	public String getArcPath() {
		return arcPath;
	}

	public void setArcPath(String arcPath) {
		this.arcPath = arcPath;
	}

	public String getDlPath() {
		return dlPath;
	}

	public void setDlPath(String dlPath) {
		this.dlPath = dlPath;
	}

	public String getPolicyPath() {
		return policyPath;
	}

	public void setPolicyPath(String policyPath) {
		this.policyPath = policyPath;
	}

	public String getIdcardPath() {
		return idcardPath;
	}

	public void setIdcardPath(String idcardPath) {
		this.idcardPath = idcardPath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getKeysNum() {
		return keysNum;
	}

	public void setKeysNum(int keysNum) {
		this.keysNum = keysNum;
	}

	public String getSelfName() {
		return selfName;
	}

	public void setSelfName(String selfName) {
		this.selfName = selfName;
	}

	public String getSelfMobile() {
		return selfMobile;
	}

	public void setSelfMobile(String selfMobile) {
		this.selfMobile = selfMobile;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public int getAcquisitioncarId() {
		return acquisitioncarId;
	}

	public void setAcquisitioncarId(int acquisitioncarId) {
		this.acquisitioncarId = acquisitioncarId;
	}

	public String getArcURLPath() {return arcURLPath;}

	public void setArcURLPath(String arcURLPath) {this.arcURLPath = arcURLPath;}

	public String getDlURLPath() {return dlURLPath;}

	public void setDlURLPath(String dlURLPath) {this.dlURLPath = dlURLPath;}

	public String getPolicyURLPath() {return policyURLPath;}

	public void setPolicyURLPath(String policyURLPath) {this.policyURLPath = policyURLPath;}

	public String getIdcardURLPath() {return idcardURLPath;}

	public void setIdcardURLPath(String idcardURLPath) {this.idcardURLPath = idcardURLPath;}
}
