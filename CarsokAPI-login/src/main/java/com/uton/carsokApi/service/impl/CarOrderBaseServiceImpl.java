package com.uton.carsokApi.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.dao.ZbRoleMapper;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.Product;
import com.uton.carsokApi.pay.lakala.model.OrderStatus;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
import com.uton.carsokApi.service.OrderBaseService;
import com.uton.carsokApi.service.OrderService;
import com.uton.carsokApi.service.SubUserService;

@Service("CAR")
public class CarOrderBaseServiceImpl implements OrderBaseService {
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
		OperateResult result = new OperateResult();
		if (request == null) {
			return new OperateResult(false, "请求参数不能为空");
		}
		Acount acount = request.getAcount();
		if (acount == null) {
			return new OperateResult(false, "请重新登录");
		}

		String subPhone = acount.getSubPhone();
		if (!StringUtils.isEmpty(subPhone) && !subUserService.querySubUserRoles(acount).contains("cwzy")) {
			return new OperateResult(false, "没有权限");
		}
		Product product = productMapper.selectByIdForUpdate(request.getProductId(), acount.getId());
		if (product == null || product.getOnShelfStatus() != 1 || 
				 product.getIsDel() == 1) {
			result.setMessage("商品状态错误,请返回重新下单。");
			return result;
		}
		if(product.getSaleStatus() == 0){
			result.setMessage("请先进行已售操作，然后在已售列表中进行收款");
			return result;
		}
		if (request.getTotalMoney() != null) {
			product.setPrice(request.getTotalMoney());
		}
		result = orderService.createOrder(product,
				acount);/**
						 * 创建订单，并生成订单加密信息 productId 生成订单
						 */
		return result;
	}

	@Override
	public OperateResult cancelOrder(OrderBaseRequest request) {
		if (request == null) {
			return new OperateResult(false, "请求参数不能为空");
		}
		Acount acount = request.getAcount();
		if (acount == null) {
			return new OperateResult(false, "请重新登录");
		}
		String subPhone = acount.getSubPhone();
		if (!StringUtils.isEmpty(subPhone) && !subUserService.querySubUserRoles(acount).contains("cwzy")) {
			return new OperateResult(false, "没有权限");
		}
		CarsokOrder order = orderService.queryByOrderNoForUpdate(request.getOrderNo());
		order.setOrderStatus(OrderStatus.ALREADY_CANCLE.name());
		order.setPayStatus(PayStatus.CANCEL_PAY.name());
		order.setMemo("用户取消");
		JSONObject description =JSONObject.parseObject(order.getDescription());
		if(description==null){
			description=new JSONObject();
		}
		description.put("canclor", StringUtils.isEmpty(subPhone)?acount.getAccount():subPhone);
		order.setDescription(description.toJSONString());
		OperateResult result = orderService.modifyOrder(order);
//		if (result.isSuccess()) {
//			BaseEvent event = new BaseEvent();
//			event.setEventName(EventConstants.CANCEL_ORDER_EVNET);
//			event.setWeight("100");
//			event.setData(request.getOrderNo());
//			eventBus.publish(event);
//		}
		return result;
	}

}
