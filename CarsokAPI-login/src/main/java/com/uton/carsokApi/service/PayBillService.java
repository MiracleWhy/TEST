package com.uton.carsokApi.service;

import java.util.List;

import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.CarsokPayBill;

public interface PayBillService {
	// 创建bill
	public OperateResult createBill(CarsokPayBill payBill);

	// 修改bill
	public OperateResult modifyBill(CarsokPayBill payBill);
	
	// 查询bill
	public CarsokPayBill queryPayBillForUpdate(String billNo);

	CarsokPayBill queryPayBill(String billNo);

	List<CarsokPayBill> queryByOrderNo(String orderNo);
	
	
}
