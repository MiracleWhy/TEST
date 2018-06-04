package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CarsokAdviceRequest;
import com.uton.carsokApi.dao.CarsokAdviceMapper;
import com.uton.carsokApi.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdviceServiceImpl implements AdviceService{

	@Autowired
	CarsokAdviceMapper carsokAdviceMapper;

	@Override
	public BaseResult insertNewAdvice(CarsokAdviceRequest carsokAdviceRequest) {
		BaseResult baseResult = BaseResult.success();
		carsokAdviceRequest.setCreateTime(new Date());
		Map result = new HashMap();
		result.put("result",carsokAdviceMapper.insertSelective(carsokAdviceRequest));
		baseResult.setData(result);
		return baseResult;
	}
}
