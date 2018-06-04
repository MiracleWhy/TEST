package com.uton.carsokApi.event.handler;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.PayWayEnum;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
import com.uton.carsokApi.pay.lakala.util.LaKaLaConstants;
import com.uton.carsokApi.service.PayBillService;

@EventHandler(name = EventConstants.PAY_NOTIFY_EVENT)
@Service
public class PayNotifyEventHandler extends EventHandle {
	@Autowired
	private PayBillService payBillService;
	@Autowired
	private EventBus eventBus;

	@Override
	public OperateResult handle(BaseEvent event) {
		JSONObject data = JSONObject.parseObject(event.getData());
		String billNo = data.getString("payOrderNo");
		String status = data.getString("return_code");
		String payWay = data.getString("payWay");
		CarsokPayBill payBill = payBillService.queryPayBillForUpdate(billNo);
		if (payBill != null) {
			BigDecimal amountPay = payBill.getBillMoney();
			if (data.getString("payAmount") != null) {
				amountPay = new BigDecimal(data.getString("payAmount"));
				if(StringUtils.equals(PayWayEnum.WECHAT.name(),payWay )){
					amountPay=amountPay.divide(new BigDecimal(100));
				}
			}
			if (StringUtils.equals(status, LaKaLaConstants.PAY_SUCCESS)) {
				payBill.setBillStatus(PayStatus.PAY_SUCCESS.name());
				payBill.setPayMoney(amountPay);
				payBill.setGmtPay(new Date());
			} else {
				payBill.setBillStatus(PayStatus.PAY_FAIL.name());
			}
			payBill.setPayWay(PayWayEnum.valueOf(payWay));
			payBill.setGmtModify(new Date());
			OperateResult result = payBillService.modifyBill(payBill);
			BaseEvent newEvent = new BaseEvent();
			newEvent.setData(payBill.getOrderNo());
			newEvent.setEventName(EventConstants.ORDER_STATUS_CHECK_EVENT);
			newEvent.setWeight("1000");
			eventBus.publish(newEvent);
			return result;
		} else {
			return new OperateResult(true, "支付订单不存在，需要人工核对");
		}
	}

}
