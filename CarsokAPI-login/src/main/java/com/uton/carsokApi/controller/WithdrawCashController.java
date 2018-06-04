package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ApplyCashRequest;
import com.uton.carsokApi.service.WithdrawCashService;

/**
 * 商家提现controller
 * @author bing.cheng
 *
 */
@Controller
public class WithdrawCashController {

	private final static Logger logger = Logger.getLogger(WithdrawCashController.class);
	
	@Autowired
	WithdrawCashService withdrawCashService;
	
	/**
	 * 申请提现
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/applyCash" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult applyCash(HttpServletRequest request, @RequestBody ApplyCashRequest vo) {
		try {
			if (!StringUtils.isNumeric(vo.getAmount())) {
				return BaseResult.fail("-1", "提现必须为数字");
			}
			return withdrawCashService.applyCash(request, vo);
		} catch (Exception e) {
			logger.error("applyCash error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 提现列表
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/cashList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult cashList(HttpServletRequest request) {
		try {
			return withdrawCashService.cashList(request);
		} catch (Exception e) {
			logger.error("cashList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
}
