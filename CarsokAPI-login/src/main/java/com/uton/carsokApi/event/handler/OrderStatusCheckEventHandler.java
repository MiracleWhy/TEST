package com.uton.carsokApi.event.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.lakala.model.OrderStatus;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
import com.uton.carsokApi.service.OrderService;
import com.uton.carsokApi.service.PayBillService;

@EventHandler(name = EventConstants.ORDER_STATUS_CHECK_EVENT)
@Service
public class OrderStatusCheckEventHandler extends EventHandle {
	@Autowired
	private OrderService orderService;
	@Autowired
	private PayBillService payBillService;
	@Autowired
	private EventBus eventBus;
	@Override
	public OperateResult handle(BaseEvent event) {
		String orderNo = event.getData();
		CarsokOrder order = orderService.queryByOrderNoForUpdate(orderNo);
		if (order == null) {
			return new OperateResult(false, "锁定订单失败");
		}

		List<CarsokPayBill> payBills = payBillService.queryByOrderNo(orderNo);
		BigDecimal payMoney = new BigDecimal("0");
		BigDecimal waitPayMoney = new BigDecimal(0);
		for (CarsokPayBill carsokPayBill : payBills) {
			if (StringUtils.equals(carsokPayBill.getBillStatus(), PayStatus.PAY_SUCCESS.name())) {
				payMoney = payMoney
						.add(carsokPayBill.getPayMoney() == null ? new BigDecimal(0) : carsokPayBill.getPayMoney());
			} else if (StringUtils.equals(carsokPayBill.getBillStatus(), PayStatus.WAIT_PAY.name())) {
				waitPayMoney = waitPayMoney
						.add(carsokPayBill.getBillMoney() == null ? new BigDecimal(0) : carsokPayBill.getBillMoney());
			}
		}
		BigDecimal restMoney = order.getTotalMoney().subtract(payMoney).subtract(waitPayMoney);
		order.setRestMoney(restMoney);
		order.setPayMoney(payMoney);
		if (payMoney.compareTo(order.getTotalMoney()) == 0 || payMoney.compareTo(order.getTotalMoney()) > 0) {
			order.setPayStatus(PayStatus.PAY_SUCCESS.name());
			order.setGmtPay(new Date());
			order.setOrderStatus(OrderStatus.ALREDAY_OVER.name());
			// TODO 发送支付成功推送
		}
		if(StringUtils.equals(order.getPayStatus(), PayStatus.PAY_SUCCESS.name())&&order.getType()==OrderTypeEnum.POS_APPLY&&order.isEnable()){
			//幂等性
			BaseEvent newEvent=new BaseEvent();
			newEvent.setEventName(EventConstants.POS_APPLY_PAY_SUCCESS_EVENT);
			newEvent.setWeight("100");
			newEvent.setData(order.getDescription());
			eventBus.publish(newEvent);
			order.setEnable(false);
		}
		OperateResult result = orderService.modifyOrder(order);
		return result;
	}

}
