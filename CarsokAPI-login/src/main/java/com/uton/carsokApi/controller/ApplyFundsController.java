package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ApplyFundsRequest;
import com.uton.carsokApi.service.ApplyFundsService;

/**
 *  配置服务controller
 * @author bing.cheng
 *
 */
@Controller
public class ApplyFundsController {
	
	private final static Logger logger = Logger.getLogger(ApplyFundsController.class);
	
	@Autowired
	ApplyFundsService applyFundsService;
	
	/**
	 * 申请配资
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/applyFunds" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult applyFunds(HttpServletRequest request, @RequestBody ApplyFundsRequest vo) {
		try {
			return applyFundsService.applyFunds(request, vo);
		} catch (Exception e) {
			logger.error("applyFunds error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/checkApplyfunds" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult checkApplyfunds(HttpServletRequest request){
		try {
			return applyFundsService.checkApplyfunds(request);
		} catch (Exception e) {
			logger.error("applyFunds error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 获取配资列表
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/getApplyFundsList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getApplyFundsList(HttpServletRequest request) {
		try {
			return applyFundsService.getApplyFundsList(request);
		} catch (Exception e) {
			logger.error("getApplyFundsList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 是否已经配资
	 */
	@RequestMapping(value = { "/ApplyFundsIf" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult ApplyFundsIf(HttpServletRequest request,@RequestBody ApplyFundsRequest vo) {
		try {
			return applyFundsService.ApplyFundsIf(request,vo);
		} catch (Exception e) {
			logger.error("ApplyFundsIf error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
}
