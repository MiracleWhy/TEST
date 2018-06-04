package com.uton.carsokApi.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.CarDataSearchRequest;
import com.uton.carsokApi.controller.request.ThirdDataSellCountRequest;
import com.uton.carsokApi.model.CarDataInfo;

public interface CarDataInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDataInfo record);

    int insertSelective(CarDataInfo record);

    CarDataInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarDataInfo record);

    int updateByPrimaryKey(CarDataInfo record);

    List<Map>  selectSellCount(ThirdDataSellCountRequest countRequest);

    Page<CarDataInfo> selectByCondition(CarDataSearchRequest carDataRequest);
}