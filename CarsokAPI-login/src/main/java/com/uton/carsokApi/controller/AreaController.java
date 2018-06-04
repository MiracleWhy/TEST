package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.service.AreaService;

@Controller
public class AreaController{
	private final static Logger logger = Logger.getLogger(AreaController.class);
	
	@Autowired
	AreaService areaService;
	
	/**
	 * 获取地区
	 * @param request
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = { "/areaInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult areaInfo(HttpServletRequest request,String pid) {
		try {
			if(pid == null){
				pid = "0";
			}
			return areaService.searchArea(Integer.parseInt(pid));
		} catch (Exception e) {
			logger.error("areaInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	@RequestMapping(value = "/city", method = RequestMethod.GET)
	public @ResponseBody BaseResult findCity(Integer id) {
		try{
			if(id != null){
				return areaService.findCity(id);
			}
			return new BaseResult(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, null);
		}catch(Exception e){
			logger.error("获取市 findCity  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
}
