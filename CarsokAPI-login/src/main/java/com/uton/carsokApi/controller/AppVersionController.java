package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.CarsokAppVersion;
import com.uton.carsokApi.service.CarsokAppVersionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class AppVersionController {

	@Resource
	CarsokAppVersionService carsokAppVersionService;

	private final static Logger logger = Logger.getLogger(AppVersionController.class);

	/**
	 * 查询不同系统迭代最新版本信息
	 */
	@RequestMapping(value = "selectAppRecentInfo",method = RequestMethod.POST)
	public @ResponseBody BaseResult selectAppRecentInfo(@RequestBody @Valid CarsokAppVersion carsokAppVersion, BindingResult result){
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			String distinction = carsokAppVersion.getDistinction();
			return carsokAppVersionService.queryRecentInfo(distinction);
		}catch (Exception e){
			logger.error("查询不同系统迭代最新版本信息 selectAppRecentInfo e: " + e);
			return BaseResult.exception(e.getMessage());
		}
	}
}
