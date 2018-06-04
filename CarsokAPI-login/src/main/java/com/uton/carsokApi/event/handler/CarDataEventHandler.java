package com.uton.carsokApi.event.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.BZYCarDataRequest;
import com.uton.carsokApi.controller.request.BaseThirdRequest;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.service.CarDataInfoService;

@EventHandler(name = EventConstants.RECEVIE_BZY_CAR_DATA)
@Service
public class CarDataEventHandler extends EventHandle {
	@Autowired
	private CarDataInfoService carDataInfoService;

	public static Logger logger = LoggerFactory.getLogger(BZYCarDataRequest.class);

	@Override
	public OperateResult handle(BaseEvent event) {
		OperateResult result = new OperateResult();
		try {
			BaseThirdRequest<BZYCarDataRequest> request = JSON.parseObject(event.getData(), BaseThirdRequest.class);
			BZYCarDataRequest bZYCarDataRequest=JSON.parseObject(JSON.toJSONString(request.getData()), BZYCarDataRequest.class);
			bZYCarDataRequest.getCarInfo().setProvider(request.getProvider());
			BaseResult baseResult = carDataInfoService.createCarDataInfoAndImage(bZYCarDataRequest
					);
			if (StringUtils.equals(baseResult.getRetCode(), "0000")) {
				result.setSuccess(true);
				return result;
			}
			result.setMessage(baseResult.getRetMsg());
			return result;
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return result;
		} finally {
			logger.info("事件处理结束: EVENT-ID " + event.getId() + "，处理结果:" + JSONObject.toJSONString(result));
		}
	}

}
