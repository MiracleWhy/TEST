package com.uton.carsokApi.constants.enums;
/**
 * pos机审批枚举 1:审核中、2:已通过、3:未通过
 * @author bing.cheng
 *
 */
public enum PosApplyStatusEnum {

	APPLING(1, "审核中"),
	APPL_YES(2, "已通过"),
	APPL_NO(3, "未通过"),
	//-------
	WAIT_SEND(4,"待发货"),
	WAIT_RECEIVE(5,"待收货"),
	APPLY_SUCCESS(6,"申请成功"),
	;
	
	private Integer code;
	private String desc;
	
	PosApplyStatusEnum(Integer code, String desc) {
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
