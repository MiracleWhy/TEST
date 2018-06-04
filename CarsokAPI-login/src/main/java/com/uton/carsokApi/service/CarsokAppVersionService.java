package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.CarsokAppVersionMapper;
import com.uton.carsokApi.model.CarsokAppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarsokAppVersionService {
	
	@Autowired
	CarsokAppVersionMapper carsokAppVersionMapper;

	/** 查询各版本最新 */
	public BaseResult queryRecentInfo(String distinction){
		BaseResult baseResult = BaseResult.success();
		CarsokAppVersion carsokAppVersion = carsokAppVersionMapper.selectRecentInfo(distinction);
		baseResult.setData(carsokAppVersion);
		return baseResult;
	}
}
