package com.uton.carsokApi.controller.response;

import java.util.List;

/**
 * 配资列表
 * 
 * @author bing.cheng
 *
 */
public class ApplyFundsListResponse {
	private int isrealName;
	private int ismerchantAudit;
	private List<ApplyFundsVo> applylist;
	public int getIsrealName() {
		return isrealName;
	}
	public void setIsrealName(int isrealName) {
		this.isrealName = isrealName;
	}
	public int getIsmerchantAudit() {
		return ismerchantAudit;
	}
	public void setIsmerchantAudit(int ismerchantAudit) {
		this.ismerchantAudit = ismerchantAudit;
	}
	public List<ApplyFundsVo> getApplylist() {
		return applylist;
	}
	public void setApplylist(List<ApplyFundsVo> applylist) {
		this.applylist = applylist;
	}
	
}
