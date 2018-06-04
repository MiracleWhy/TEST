package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.PosApplyRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.PosApply;

/**
 * 
 * @author jml
 *
 *         2017年6月5日
 */
public interface PosApplyService {

	public PosApply queryApplyInfo(String accountId);    
	
	public OperateResult create(PosApply posApply);

	public OperateResult modify(PosApply posApply);
	
	public OperateResult createApplyOrder(PosApplyRequest request);
}
