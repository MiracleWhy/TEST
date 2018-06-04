package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.PosPay;
import com.uton.carsokApi.model.PosPayDetail;

public interface PosPayMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PosPay record);

    PosPay selectByModel(PosPay record);

    int updateByPrimaryKeySelective(PosPay record);

	List<PosPay> selectToPayList(Integer acountId);

	List<PosPay> selectPayedList(Integer acountId);
	
	List<PosPayDetail> selectPayedDetail(Integer pid);
}