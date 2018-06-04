package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.CarsokPayBill;

public interface CarsokPayBillMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CarsokPayBill record);

	int insertSelective(CarsokPayBill record);

	CarsokPayBill selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CarsokPayBill record);

	int updateByPrimaryKey(CarsokPayBill record);

	List<CarsokPayBill> selectOrderBills(String orderNo);

	CarsokPayBill selectBillForUpdate(String billNo);

	CarsokPayBill selectBill(String billNo);
}