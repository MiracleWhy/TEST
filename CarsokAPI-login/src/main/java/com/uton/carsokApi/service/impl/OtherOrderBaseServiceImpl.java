package com.uton.carsokApi.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.dao.AccountLklMapper;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.AccountLkl;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.pay.lakala.util.SignUtils;
import com.uton.carsokApi.service.OrderBaseService;
import com.uton.carsokApi.service.OrderService;
@Service("OTHER")
public class OtherOrderBaseServiceImpl implements OrderBaseService{
	@Autowired
	private OrderService orderService;
	@Autowired
	private AccountLklMapper accountLklMapper;
 	@Override
	public OperateResult createOrder(OrderBaseRequest request) {
		if(request==null){
			return new OperateResult(false,"请求参数不能为空");
		}
		if(request.getAcount()==null){
			return new OperateResult(false,"请重新登录");
		}
		AccountLkl accountLkl = accountLklMapper.selectByAccountIdForUpdate(request.getAcount().getAccount());
		CarsokOrder order=new CarsokOrder();
		order.setAccountId(request.getAcount().getId()+"");
		order.setTotalMoney(request.getTotalMoney());
		String orderNo = SignUtils.getOrderNo();
		order.setOrderNo(orderNo);
		order.setBizId(orderNo);
		order.setType(OrderTypeEnum.OTHER);
		order.setAccountRealName(accountLkl==null?"":accountLkl.getRealName());
		JSONObject description=new JSONObject();
		description.put("productName", request.getProductName());
		description.put("img", "");
		description.put("price",order.getTotalMoney());
		description.put("phoneNumber", "");
		description.put("name", "");
		description.put("remark", request.getRemark());
		description.put("salesmanName", request.getSalesmanName());
		order.setDescription(JSONObject.toJSONString(description));
		OperateResult result=orderService.create(order);
		result.setData(order);
		return result;
	}


	@Override
	public OperateResult cancelOrder(OrderBaseRequest reqeust) {
		return orderService.cancelOrder(reqeust);
	}

}
