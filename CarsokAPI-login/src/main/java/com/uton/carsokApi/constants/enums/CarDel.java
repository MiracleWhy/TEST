package com.uton.carsokApi.constants.enums;
/**
 * 车辆删除枚举 是否删除 0 未删除 1 删除
 * @author bing.cheng
 *
 */
public enum CarDel {

	DEL_NO(0, "未删除"),
	DEL_YES(1, "删除");
	
	private Integer code;
	private String desc;
	
	CarDel(Integer code, String desc) {
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
