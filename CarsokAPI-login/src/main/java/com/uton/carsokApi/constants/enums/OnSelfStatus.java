package com.uton.carsokApi.constants.enums;
/**
 * 上架状态枚举 上架状态 0 未上架 1 上架
 * @author bing.cheng
 *
 */
public enum OnSelfStatus {
	OFF_SELF(0, "下架"),
	ON_SELF(1, "上架"),
	ON_CHECK(2, "审核中"),
	ON_CHECK_NO(3, "审核未通过"),
	ON_NEW(4,"新建待提交审核");
	
	
	private Integer code;
	private String desc;
	
	OnSelfStatus(Integer code, String desc) {
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
