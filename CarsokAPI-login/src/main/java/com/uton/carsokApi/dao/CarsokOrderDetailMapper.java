package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.CarsokOrderDetail;

public interface CarsokOrderDetailMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CarsokOrderDetail record);

	int insertSelective(CarsokOrderDetail record);

	CarsokOrderDetail selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CarsokOrderDetail record);

	int updateByPrimaryKey(CarsokOrderDetail record);

	List<CarsokOrderDetail> selectOrderDetails(String orderNo);
}