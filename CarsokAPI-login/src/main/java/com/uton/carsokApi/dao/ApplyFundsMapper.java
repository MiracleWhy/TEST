package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.ApplyFunds;

public interface ApplyFundsMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ApplyFunds record);

    ApplyFunds selectByModel(ApplyFunds record);
    
    List<ApplyFunds> selectListByModel(ApplyFunds record);

    int updateByPrimaryKeySelective(ApplyFunds record);

    ApplyFunds selectStatusByModel(ApplyFunds record);

}