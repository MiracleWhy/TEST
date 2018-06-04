package com.uton.carsokApi.service.impl;

import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.PayTool;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.service.PayBaseService;
import com.uton.carsokApi.service.PayBillService;

@Service("WECHAT")
public class WechatPayBaseServiceImpl implements PayBaseService{
	@Autowired
	private PayBillService payBillService;
	@Value("${wx.sys.notifyurl}")
	private String wx_sys_notify_url;
	@Override
	public OperateResult sign(PayBillRequest request) throws Exception {
		//查询订单是否存在
		CarsokPayBill payBill = payBillService.queryPayBill(request.getBillNo());
		if (payBill == null) {
			return new OperateResult(false, "订单不存在，请返回重试。");
		}
		SortedMap<String, Object> restMap = PayTool.wxPay(payBill.getBillNo(), payBill.getBillMoney(),request.getIp(),payBill.getBody(),wx_sys_notify_url,"");
		return new OperateResult(true,"",restMap);
	}
	
}
