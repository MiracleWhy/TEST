package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.AcountBank;

public interface AcountBankMapper {
    int deleteByByModel(AcountBank record);

    int insertSelective(AcountBank record);

    AcountBank selectByModel(AcountBank record);

    int updateByPrimaryKeySelective(AcountBank record);

}