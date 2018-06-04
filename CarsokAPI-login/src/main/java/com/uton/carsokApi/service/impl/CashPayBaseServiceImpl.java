package com.uton.carsokApi.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
import com.uton.carsokApi.service.PayBaseService;
import com.uton.carsokApi.service.PayBillService;

@Service("CASH")
public class CashPayBaseServiceImpl implements PayBaseService {
	@Autowired
	private PayBillService payBillService;
	@Autowired
	private EventBus eventBus;
	@Override
	public OperateResult sign(PayBillRequest request) throws Exception {
		CarsokPayBill payBill = payBillService.queryPayBillForUpdate(request.getBillNo());
		// 直接进行支付操作 发起订单check事件
		if (payBill == null) {
			return new OperateResult(false, "订单不存在，请返回重试。");
		}
		Acount acount = request.getAcount();
		payBill.setGmtPay(new Date());
		JSONObject description = new JSONObject();
		description.put("creator",
				StringUtils.isEmpty(acount.getSubPhone()) ? acount.getAccount() : acount.getSubPhone());
		payBill.setPayMoney(payBill.getBillMoney());
		payBill.setPayWay(request.getPayWay());
		payBill.setBillStatus(PayStatus.PAY_SUCCESS.name());
		OperateResult result = payBillService.modifyBill(payBill);
		BaseEvent event=new BaseEvent();
		event.setEventName(EventConstants.ORDER_STATUS_CHECK_EVENT);
		event.setData(payBill.getOrderNo());
		eventBus.publish(event);
		return result;
	}

}
