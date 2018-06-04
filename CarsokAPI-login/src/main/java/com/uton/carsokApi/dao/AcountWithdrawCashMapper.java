package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.AcountWithdrawCash;

public interface AcountWithdrawCashMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AcountWithdrawCash record);

    AcountWithdrawCash selectByModel(AcountWithdrawCash record);
    
    List<AcountWithdrawCash> selectListByModel(AcountWithdrawCash record);
    
    int updateByPrimaryKeySelective(AcountWithdrawCash record);

}