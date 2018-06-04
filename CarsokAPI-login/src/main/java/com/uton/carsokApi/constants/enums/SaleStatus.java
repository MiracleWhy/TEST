package com.uton.carsokApi.constants.enums;
/**
 * 销售状态枚举 销售状态 0 在售  1售出
 * @author bing.cheng
 *
 */
public enum SaleStatus {

	ON_SALE(0, "在售"),
	SALED(1, "售出");
	
	private Integer code;
	private String desc;
	
	SaleStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	


}
