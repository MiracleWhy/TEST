package com.uton.carsokApi.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.dao.CarsokOrderDetailMapper;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.CarDataImage;
import com.uton.carsokApi.model.CarDataInfo;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.CarsokOrderDetail;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.pay.lakala.model.ProductStatus;
import com.uton.carsokApi.pay.lakala.util.SignUtils;
import com.uton.carsokApi.service.CarDataInfoService;
import com.uton.carsokApi.service.OrderBaseService;
import com.uton.carsokApi.service.OrderService;
@Service("DATA")
public class DataOrderBaseServiceImpl implements OrderBaseService{
	@Autowired
	private CarDataInfoService carDataInfoService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CarsokOrderDetailMapper orderDetailMapper;
	@Autowired
	private RedisTemplate redisTemplate;
 	@Override
	public OperateResult createOrder(OrderBaseRequest request) {
		if(request==null){
			return new OperateResult(false,"请求参数不能为空");
		}
		CarDataInfo carDataInfo=carDataInfoService.queryById(request.getProductId());
		if(carDataInfo==null){
			return new OperateResult(false,"商品不存在");
		}
		if(request.getAcount()==null){
			return new OperateResult(false,"请重新登录");
		}
		CarsokOrder order=new CarsokOrder();
		order.setAccountId(request.getAcount().getId()+"");
		order.setTotalMoney(carDataInfoService.getPrice(carDataInfo));
		String orderNo = SignUtils.getOrderNo();
		order.setOrderNo(orderNo);
		order.setBizId(orderNo);
		order.setType(OrderTypeEnum.DATA);
		JSONObject description=new JSONObject();
		CarDataImage carImage=carDataInfoService.queryCarImage(carDataInfo.getUuid());
		description.put("productName", carDataInfo.getCarBrand()+" "+carDataInfo.getCarSeries());
		description.put("img", carImage!=null?carImage.getUrl():"");
		description.put("price",order.getTotalMoney());
		description.put("phoneNumber", carDataInfo.getPhone());
		description.put("name", carDataInfo.getDealerName());
		order.setDescription(JSONObject.toJSONString(carDataInfo));
		CarsokOrderDetail orderDetail=new CarsokOrderDetail();
		orderDetail.setOrderNo(orderNo);
		orderDetail.setProductCount(1);
		orderDetail.setProductId(request.getProductId());
		orderDetail.setProductPrice(carDataInfoService.getPrice(carDataInfo));
		orderDetail.setStatus(ProductStatus.FREEZE.name());
		orderDetail.setGmtModify(new Date());
		OperateResult result=orderService.create(order);
		orderDetailMapper.insert(orderDetail);
		PayBillRequest billRequest=new PayBillRequest();
		billRequest.setOrderNo(orderNo);
		billRequest.setMoney(order.getTotalMoney());
		billRequest.setAcount(request.getAcount());
		billRequest.setBody(order.getType().message());
		result=orderService.createBill(billRequest);
		ValueOperations<String, String> operations=redisTemplate.opsForValue();
		operations.set(orderNo, carDataInfo.getPhone());
		return result;
	}


	@Override
	public OperateResult cancelOrder(OrderBaseRequest reqeust) {
		return orderService.cancelOrder(reqeust);
	}

}
