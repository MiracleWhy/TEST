package com.uton.carsokApi.constants.enums;
/**
 * pos机开通枚举 1:未开通、2:已开通
 * @author bing.cheng
 *
 */
public enum PosOpenStatusEnum {

	OPEN_NO(1, "未开通"),
	OPEN_YES(2, "已开通");
	
	private Integer code;
	private String desc;
	
	PosOpenStatusEnum(Integer code, String desc) {
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
