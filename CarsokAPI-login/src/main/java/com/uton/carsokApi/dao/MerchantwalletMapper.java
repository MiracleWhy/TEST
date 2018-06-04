package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.Merchantwallet;

public interface MerchantwalletMapper {
	int insertSelective(Merchantwallet record);
	
	Merchantwallet selectByModel(Merchantwallet record);
	
	int updateByPrimaryKeySelective(Merchantwallet record); 
}
