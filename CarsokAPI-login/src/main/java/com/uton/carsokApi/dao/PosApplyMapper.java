package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.PosApply;

public interface PosApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PosApply record);

    int insertSelective(PosApply record);

    PosApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PosApply record);

    int updateByPrimaryKey(PosApply record);

	PosApply selectByAccountId(String accountId);
}