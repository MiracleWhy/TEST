package com.uton.carsokApi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.service.OrderBaseService;
import com.uton.carsokApi.service.OrderService;

@Service
public class OrderHelper implements BeanPostProcessor {

	private Map<String, OrderBaseService> map = new HashMap<>();
	@Autowired
	private OrderService orderService;
	@Transactional
	public OperateResult createOrder(OrderBaseRequest request) {
		if (request == null) {
			return new OperateResult(false, "请求参数不能为空");
		}
		if (request.getType() == null) {
			return new OperateResult(false, "订单类型不能为空");
		}
		OrderBaseService service = map.get(request.getType().name());
		if (service == null) {
			return new OperateResult(false, "不支持该种类型订单");
		}
		OperateResult result = service.createOrder(request);

		return result;
	}

	public OperateResult cancelOrder(OrderBaseRequest request) {
		if (request == null) {
			return new OperateResult(false, "请求参数不能为空");
		}
		CarsokOrder order = orderService.queryByOrderNo(request.getOrderNo());
		if (order == null) {
			return new OperateResult(false, "订单不存在");
		}
		OrderBaseService service = map.get(order.getType().name());
		if (service == null) {
			return new OperateResult(false, "不支持该种类型订单");
		}
		OperateResult result = service.cancelOrder(request);
		return result;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof OrderBaseService) {
			map.put(beanName, (OrderBaseService) bean);
		}
		return bean;
	}

}
