package com.uton.carsokApi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.PayTool;
import com.uton.carsokApi.pay.alipay.PayConfig;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.service.PayBaseService;
import com.uton.carsokApi.service.PayBillService;

@Service("ALI")
public class AliPayBaseServiceImpl implements PayBaseService {
	@Autowired
	private PayBillService payBillService;
	@Value("${ali.sys.notifyurl}")
	private String ali_sys_notify_url;
	@Override
	public OperateResult sign(PayBillRequest request) {
		//
		CarsokPayBill payBill = payBillService.queryPayBill(request.getBillNo());
		if (payBill == null) {
			return new OperateResult(false, "订单不存在，请返回重试。");
		}
		Map<String, Object> map = new HashMap();
		map.put("body", payBill.getBody());
		map.put("total_fee", payBill.getBillMoney());
		map.put("out_trade_no", payBill.getBillNo());
		map.put("notify_url", ali_sys_notify_url);
		Map signMap = PayTool.toPay(map);
		if (signMap != null) {
			return new OperateResult(true, "签名成功", signMap);
		}
		return new OperateResult(false, "签名失败");
	}

}
