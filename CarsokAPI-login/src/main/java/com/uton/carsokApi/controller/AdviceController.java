package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.CarsokAdviceRequest;
import com.uton.carsokApi.service.AdviceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AdviceController {

	private final static Logger logger = Logger.getLogger(AppVersionController.class);

	@Resource
	AdviceService adviceService;

	/**
	 * Insert Advice
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "insertAdvice",method = RequestMethod.POST)
	@ResponseBody
	public BaseResult insertAdvice(HttpServletRequest request, @RequestBody @Valid CarsokAdviceRequest carsokAdviceRequest, BindingResult result){
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return adviceService.insertNewAdvice(carsokAdviceRequest);
		}catch(Exception e){
			logger.error("新增意见信息 insertAdvice e: " + e);
			return BaseResult.exception(e.getMessage());
		}
	}
}
