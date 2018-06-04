package com.uton.carsokApi.controller.response;
/**
 *  是否绑定银行卡 返回值
 * @author bing.cheng
 *
 */
public class IsBindCardResponse {
	/** 1 未绑定 2绑定*/
	private Short isBind;

	public Short getIsBind() {
		return isBind;
	}

	public void setIsBind(Short isBind) {
		this.isBind = isBind;
	}
	
	
}
