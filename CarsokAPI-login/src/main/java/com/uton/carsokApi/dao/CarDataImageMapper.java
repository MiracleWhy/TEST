package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.CarDataImage;

public interface CarDataImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDataImage record);

    int insertSelective(CarDataImage record);

    CarDataImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarDataImage record);

    int updateByPrimaryKey(CarDataImage record);

	CarDataImage selectOneByUuid(String uuid);
}