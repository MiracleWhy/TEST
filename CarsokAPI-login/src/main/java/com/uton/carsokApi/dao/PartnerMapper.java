package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.Partner;

public interface PartnerMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Partner record);

	int insertSelective(Partner record);

	Partner selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Partner record);

	int updateByPrimaryKey(Partner record);

	Partner selectPartner(String partner, String token);
}