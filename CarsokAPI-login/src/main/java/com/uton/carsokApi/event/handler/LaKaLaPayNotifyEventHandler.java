package com.uton.carsokApi.event.handler;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.constants.enums.PayWayEnum;
import com.uton.carsokApi.dao.AccountLklMapper;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.AccountLkl;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.lakala.model.LaKaLaApplyStatusEnum;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
import com.uton.carsokApi.pay.lakala.util.LaKaLaConstants;
import com.uton.carsokApi.service.PayBillService;

@EventHandler(name = EventConstants.LAKALA_PAY_NOTIFY_EVENT)
@Service
public class LaKaLaPayNotifyEventHandler extends EventHandle {
	@Autowired
	private PayBillService payBillService;
	@Autowired
	private EventBus eventBus;
	@Autowired
	private AcountMapper accountMapper;
	@Autowired
	private AccountLklMapper accountLklMapper;
	/**
	 * 拉卡拉支付通知
	 */
	@Override
	public OperateResult handle(BaseEvent event) {
		JSONObject data = JSONObject.parseObject(event.getData());
		String billNo = data.getString("partnerBillNo");
		BigDecimal amountPay = new BigDecimal(data.getString("amountPay"));
		String isSuccess = data.getString("isSuccess");
		CarsokPayBill payBill = payBillService.queryPayBillForUpdate(billNo);
		if (payBill != null) {
			if (StringUtils.equals(isSuccess, LaKaLaConstants.SUCCESS)) {
				payBill.setBillStatus(PayStatus.PAY_SUCCESS.name());
				payBill.setPayMoney(amountPay);
				payBill.setGmtPay(new Date());
			} else {
				payBill.setBillStatus(PayStatus.PAY_FAIL.name());
			}
			payBill.setGmtModify(new Date());
			payBill.setPayWay(PayWayEnum.LAKALA);
			OperateResult result = payBillService.modifyBill(payBill);
			if (StringUtils.equals(OrderTypeEnum.TEST.name(), payBill.getMemo()) ) {
				// 修改拉卡拉开户状态
				AccountLkl accountLkl = new AccountLkl();
				accountLkl.setAccountId(payBill.getAccountId());
				accountLkl.setApplyStatus(LaKaLaApplyStatusEnum.WAIT_UPLOAD_TRADE_CERTIFICATE);
				accountLklMapper.updateByPrimaryKeySelective(accountLkl);
				return new OperateResult(true, "");
			}
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