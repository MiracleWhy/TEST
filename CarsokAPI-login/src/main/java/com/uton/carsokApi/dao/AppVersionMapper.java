package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.AppVersion;

public interface AppVersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);
    
    List<AppVersion> selectByPage(AppVersion record);

}