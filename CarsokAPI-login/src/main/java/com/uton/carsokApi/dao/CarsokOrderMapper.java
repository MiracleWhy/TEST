package com.uton.carsokApi.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.model.CarsokOrder;

public interface CarsokOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarsokOrder record);

    int insertSelective(CarsokOrder record);

    CarsokOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarsokOrder record);

    int updateByPrimaryKey(CarsokOrder record);
    //	TODO
	CarsokOrder selectByOrderNoForUpdate(String orderNo);

	CarsokOrder selectOrderStatus(String orderNo);

	CarsokOrder selectByOrderNo(String orderNo);

	Page<CarsokOrder> selectOrdersByStatus(OrderBaseRequest request);

	List<String> selectProductIdsByCondition(CarsokOrder order);

	List<Map <String,Object>> selectOrderCount(OrderBaseRequest baseRequests);

	CarsokOrder selectTestOrderForUpdate();

	List<Map<String,String>> selectByProductIdsAndType(List<String> ids, OrderTypeEnum car);
}