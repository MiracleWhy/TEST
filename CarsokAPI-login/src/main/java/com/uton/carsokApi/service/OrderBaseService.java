package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.event.constants.OperateResult;

public interface OrderBaseService {
	
	public static  String type="default";
	public OperateResult createOrder(OrderBaseRequest request);
	public OperateResult cancelOrder(OrderBaseRequest reqeust);
}
