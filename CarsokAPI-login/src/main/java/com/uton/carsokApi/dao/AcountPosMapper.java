package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.AcountPos;

public interface AcountPosMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AcountPos record);

    int insertSelective(AcountPos record);

    AcountPos selectByModel(AcountPos record);

    int updateByPrimaryKeySelective(AcountPos record);

    int updateByPrimaryKey(AcountPos record);
}