package com.uton.carsokApi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.service.PayBaseService;

@Service
public class PayHelper implements BeanPostProcessor{
	
	private Map<String, PayBaseService> map = new HashMap<String, PayBaseService>();
	@Transactional
	public OperateResult sign(PayBillRequest request) throws Exception {
		PayBaseService payService=map.get(request.getPayWay().name());
		if(payService==null){
			return null;
		}
		return payService.sign(request);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof PayBaseService) {
			map.put(beanName, (PayBaseService)bean);
		}
		return bean;
	}
}
