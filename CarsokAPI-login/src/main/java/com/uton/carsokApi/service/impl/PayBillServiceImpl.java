package com.uton.carsokApi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.dao.CarsokPayBillMapper;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
import com.uton.carsokApi.pay.lakala.util.SignUtils;
import com.uton.carsokApi.service.PayBillService;

@Service
public class PayBillServiceImpl implements PayBillService {
	@Autowired
	private CarsokPayBillMapper payBillMapper;

	@Override
	public OperateResult createBill(CarsokPayBill payBill) {
		payBill.setBillNo(SignUtils.getOrderNo());
		payBill.setBillStatus(PayStatus.WAIT_PAY.name());
		payBill.setEnable(true);
		payBill.setGmtModify(new Date());
		payBillMapper.insert(payBill);
		OperateResult result = new OperateResult();
		result.setSuccess(true);
		result.setData(payBill);
		return result;
	}

	@Override
	public OperateResult modifyBill(CarsokPayBill payBill) {
		payBill.setGmtModify(new Date());
		payBillMapper.updateByPrimaryKeySelective(payBill);
		return new OperateResult(true, "");
	}

	@Override
	public CarsokPayBill queryPayBillForUpdate(String billNo) {

		return payBillMapper.selectBillForUpdate(billNo);
	}
	@Override
	public CarsokPayBill queryPayBill(String billNo) {

		return payBillMapper.selectBill(billNo);
	}

	@Override
	public List<CarsokPayBill> queryByOrderNo(String orderNo) {
		
		return payBillMapper.selectOrderBills(orderNo);
	}

}
