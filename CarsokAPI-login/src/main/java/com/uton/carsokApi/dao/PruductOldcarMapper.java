package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.PruductOldcar;

public interface PruductOldcarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PruductOldcar record);

    int insertSelective(PruductOldcar record);

    PruductOldcar selectByModel(PruductOldcar record);

    int updateByPrimaryKeySelective(PruductOldcar record);

    int updateByPrimaryKey(PruductOldcar record);

    int updateVehicleSupervise();

}