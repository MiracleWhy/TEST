package com.uton.carsokApi.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.controller.request.ApplyAccountInfo;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.controller.request.PosApplyRequest;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.pay.lakala.util.SignUtils;
import com.uton.carsokApi.service.OrderBaseService;
import com.uton.carsokApi.service.OrderService;
import com.uton.carsokApi.service.SubUserService;
@Service("POS_APPLY")
public class PosOrderBaseServiceImpl implements OrderBaseService {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private EventBus eventBus;
	@Autowired
	private SubUserService subUserService;
	@Override
	public OperateResult createOrder(OrderBaseRequest request) {
		PosApplyRequest applyRequest=(PosApplyRequest)request;
		OperateResult result = new OperateResult();
		if (request == null) {
			return new OperateResult(false, "请求参数不能为空");
		}
		Acount acount = applyRequest.getAcount();
		if (acount == null) {
			return new OperateResult(false, "请重新登录");
		}
		ApplyAccountInfo applyAccountInfo=new ApplyAccountInfo();
		String subPhone = acount.getSubPhone();
		applyAccountInfo.setMobile(StringUtils.isEmpty(subPhone)?acount.getMobile():subPhone);
		applyAccountInfo.setName(StringUtils.isEmpty(subPhone)?acount.getMerchantName():subPhone);
		applyRequest.setApplyAccountInfo(applyAccountInfo);
		if (!StringUtils.isEmpty(subPhone) && !subUserService.querySubUserRoles(acount).contains("cwzy")) {
			return new OperateResult(false, "没有权限");
		}
		CarsokOrder order=new CarsokOrder();
		order.setOrderNo(SignUtils.getOrderNo() );
		order.setAccountId(acount.getMobile());
		applyRequest.getApplyInfo().setOrderNo(order.getOrderNo());
		order.setDescription(JSONObject.toJSONString(applyRequest));
		order.setType(applyRequest.getType());
		order.setTotalMoney(new BigDecimal("99"));
		result=orderService.create(order);
		if(!result.isSuccess()){
			return result;
		}
		PayBillRequest billRequest=new PayBillRequest();
		billRequest.setOrderNo(order.getOrderNo());
		billRequest.setBody("pos机器购买");
		billRequest.setMoney(order.getTotalMoney());
		billRequest.setAcount(acount);
		result=orderService.createBill(billRequest);
		//创建订单
		//创建流水
		//提交支付请求
		return result;
	}

	@Override
	public OperateResult cancelOrder(OrderBaseRequest reqeust) {
		// TODO Auto-generated method stub
		return null;
	}

}
