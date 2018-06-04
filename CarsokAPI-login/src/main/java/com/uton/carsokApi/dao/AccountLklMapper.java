package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.AccountLkl;

public interface AccountLklMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountLkl record);

    int insertSelective(AccountLkl record);

    AccountLkl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountLkl record);

    int updateByPrimaryKey(AccountLkl record);

	AccountLkl selectByAccountIdForUpdate(String valueOf);
}