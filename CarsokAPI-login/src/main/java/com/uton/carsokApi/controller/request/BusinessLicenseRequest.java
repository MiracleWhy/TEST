package com.uton.carsokApi.controller.request;
/**
 * 申请商家三无认证 request
 * @author bing.cheng
 *
 */
public class BusinessLicenseRequest {
	/** 2.4.6以前使用此字段**/
	private String licencePath;

	/**2..6开始使用此字段**/
	private String licenceURLPath;

	public String getLicencePath() {
		return licencePath;
	}

	public void setLicencePath(String licencePath) {
		this.licencePath = licencePath;
	}

	public String getLicenceURLPath() {return licenceURLPath;}

	public void setLicenceURLPath(String licenceURLPath) {this.licenceURLPath = licenceURLPath;}
}
