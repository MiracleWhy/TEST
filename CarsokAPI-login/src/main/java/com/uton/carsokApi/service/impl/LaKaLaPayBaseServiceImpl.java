package com.uton.carsokApi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.pay.lakala.util.SignUtils;
import com.uton.carsokApi.service.PayBaseService;
import com.uton.carsokApi.service.PayBillService;

@Service("LAKALA")
public class LaKaLaPayBaseServiceImpl implements PayBaseService {
	@Autowired
	private PayBillService payBillService;

	@Override
	public OperateResult sign(PayBillRequest request) {
		Acount acount = request.getAcount();
		CarsokPayBill payBill = payBillService.queryPayBill(request.getBillNo());
		Map<String, Object> map = getInitMap();
		map.put("userId", acount.getAccount());//acount.getId()
		map.put("phoneNumber", acount.getAccount());
		map.put("timestamp", SignUtils.getTimestamp());
		map.put("orderId", payBill.getBillNo());
		map.put("remark", "交易");
		map.put("productName", "车辆交易");
		map.put("productDesc", "车辆交易");
		map.put("amount", payBill.getBillMoney());
		map.put("randnum", SignUtils.getRandomNum(6));
		map.put("optCode", "P00001");
		map.put("realName",payBill.getAccountRealName());//acount.getRealName()
		map.put("idCardId", acount.getIdcard());//acount.getIdcard()
		map.put("idCardNo", acount.getIdcard());
		map.put("srcPartnerOrderNo", payBill.getBillNo());
		map.put("mercId", payBill.getBillNo());
		map.put("expriredtime", SignUtils.getExpireTimestamp());
		map.put("callbackUrl", "lklCashierDemo://");
		map.put("tranceCardType", null);
		map.put("transCardNo", null);
		OperateResult result = SignUtils.sign(map);
		
		map.clear();
		map.put("sign", result.getData());
		map.put("bill", payBill);
		result.setData(map);
		return result;
	}

	private Map<String, Object> getInitMap() {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

}
