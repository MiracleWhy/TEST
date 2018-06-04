package com.uton.carsokApi.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.service.CustomMessagePushService;

@EventHandler(name = EventConstants.UPDATE_QUOTIENT_SCORE)
@Service
public class UpdateQuotientScoreEventHandler extends EventHandle {
	@Autowired
	private CustomMessagePushService customMessagePushService;

	@Override
	public OperateResult handle(BaseEvent event) {
		JSONObject json=JSONObject.parseObject(event.getData());
		Integer id=json.getInteger("id");
		int i=customMessagePushService.updateQuotientScoreByMerchant(null, id);
		OperateResult result=new OperateResult();
		result.setSuccess(true);
		result.setData(i);
		result.setMessage(i+"");
		return result;
	}
}
