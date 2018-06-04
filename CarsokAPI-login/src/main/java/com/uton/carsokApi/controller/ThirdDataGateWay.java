package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.controller.request.BZYCarDataRequest;
import com.uton.carsokApi.controller.request.BaseThirdRequest;
import com.uton.carsokApi.controller.request.ThirdDataSellCountRequest;
import com.uton.carsokApi.controller.response.ThirdDataSellCountResponse;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventStatusEnum;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.pay.lakala.model.OrderStatus;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
import com.uton.carsokApi.service.CarDataInfoService;

@Controller
@RequestMapping("third")
public class ThirdDataGateWay {
	@Autowired
	private CarDataInfoService carDataInfoService;
	@Autowired
	private EventBus eventBus;
	
	@RequestMapping("thirdCarData")
	@ResponseBody
	public BaseResult getData(HttpServletRequest request,
			@Valid @RequestBody BaseThirdRequest<BZYCarDataRequest> dataRequest, BindingResult bindresult) {
		if (bindresult.hasErrors()) {
			return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
		}
		// 认证partner身份
		BaseResult result = carDataInfoService.validatePartner(dataRequest.getPartner(), dataRequest.getToken());
		// 成功直接存库
		if (StringUtils.equals(result.getRetCode(), ErrorCode.SuccessRetCode)) {
			dataRequest.setProvider(result.getData().toString());
			BaseEvent event = new BaseEvent();
			event.setData(JSONObject.toJSONString(dataRequest));
			event.setEventName(EventConstants.RECEVIE_BZY_CAR_DATA);
			event.setEventStatus(EventStatusEnum.WAIT_HANDLE.name());
			event.setWeight("10");
			return eventBus.publish(event);
		}
		return result;
	}

	@RequestMapping("getSellCount")
	@ResponseBody
	public BaseResult getThirdDataSellCount(HttpServletRequest request,
			@Valid @RequestBody BaseThirdRequest<ThirdDataSellCountRequest> dataRequest, BindingResult bindresult) {
		if (bindresult.hasErrors()) {
			return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
		}
		// 认证partner身份
		BaseResult result = carDataInfoService.validatePartner(dataRequest.getPartner(), dataRequest.getToken());
		if (StringUtils.equals(result.getRetCode(), ErrorCode.SuccessRetCode)) {
			//查询支付成功的订单
			ThirdDataSellCountRequest  countRequest=JSON.parseObject(JSONObject.toJSONString(dataRequest.getData()), ThirdDataSellCountRequest.class,JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
			countRequest.setProvider(result.getData().toString());
			countRequest.setPayStatus(PayStatus.PAY_SUCCESS);
			countRequest.setType(OrderTypeEnum.DATA.name());
			countRequest.setOrderStatus(OrderStatus.ALREDAY_OVER);
			ThirdDataSellCountResponse response=carDataInfoService.querySellCount(countRequest);
			return BaseResult.success(response);
		}
		return result;
	}
}
