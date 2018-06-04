package com.uton.carsokApi.pay.lakala.model;

public enum LaKaLaApplyStatusEnum {
	LAKALA_OPEN_SUCCESS("拉卡拉开通成功"),
	TRADE_TEST_COMPILE("交易测试完成"),
	TRADE_CERTIFICATE_SUBMIT_SUCCESS("交易凭证上传成功"),
	
	
	
	WAIT_TRADE_TEST("待交易测试"),
	WAIT_UPLOAD_TRADE_CERTIFICATE("待上传交易凭证"),
	WAIT_REVIEW("待审核"),
	REVIEW_SUCCESS("审核成功"),
	REVIEW_FAIL("审核失败")
	;
	String message;
	private LaKaLaApplyStatusEnum(String message) {
		this.message=message;
	}
	public String message(){
		return message;
	}
}
