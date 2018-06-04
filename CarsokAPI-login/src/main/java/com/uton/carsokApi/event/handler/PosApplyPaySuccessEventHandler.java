package com.uton.carsokApi.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.PosApplyStatusEnum;
import com.uton.carsokApi.controller.request.PosApplyRequest;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.PosApply;
import com.uton.carsokApi.service.PosApplyService;
@EventHandler(name=EventConstants.POS_APPLY_PAY_SUCCESS_EVENT)
@Service
public class PosApplyPaySuccessEventHandler extends EventHandle{
	@Autowired
	private PosApplyService posApplyService;
	@Override
	public OperateResult handle(BaseEvent event) {
		String data=event.getData();
		//创建
		PosApplyRequest request=JSON.parseObject(data, PosApplyRequest.class);
		PosApply posApply=new PosApply();
		posApply.setApplyAccountInfo(JSONObject.toJSONString(request.getApplyAccountInfo()));
		posApply.setAccountId(request.getAccountId());
		posApply.setApplyInfo(JSONObject.toJSONString(request.getApplyInfo()));
		posApply.setBizId(EventConstants.POS_APPLY_PAY_SUCCESS_EVENT+request.getAccountId());
		posApply.setApplyStatus(PosApplyStatusEnum.WAIT_SEND);
		return posApplyService.create(posApply);
	}

}
