package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.PosPayDetail;

public interface PosPayDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PosPayDetail record);

    int insertSelective(PosPayDetail record);

    PosPayDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PosPayDetail record);

    int updateByPrimaryKey(PosPayDetail record);
}