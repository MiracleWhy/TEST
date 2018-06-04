package com.uton.carsApi.test.order;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.service.impl.OrderHelper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml",
        "classpath:xmlConfig/application.xml",
        "classpath:xmlConfig/KafkaProducer.xml",
        "classpath:xmlConfig/spring-db.xml",
        "classpath:xmlConfig/mybatis-config.xml"})
public class OtherOrderCreateTest {
	@Autowired
	private OrderHelper orderHelper;
	
	@Test
	public void createOrder(){
		OrderBaseRequest request=new OrderBaseRequest();
		request.setTotalMoney(new BigDecimal("100"));
		request.setType(OrderTypeEnum.OTHER);
		request.setProductName("测试其他订单");
		request.setRemark("测试");
		OperateResult result=orderHelper.createOrder(request);
		System.out.println(JSONObject.toJSONString(result));
	}
}
