package com.uton.carsokApi.service;

import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;

public interface PayBaseService {
	//获取加密信息
	public OperateResult sign(PayBillRequest request) throws Exception;
}
