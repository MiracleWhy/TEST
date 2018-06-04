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
import com.uton.carsokApi.controller.request.BindBankCardRequest;
import com.uton.carsokApi.service.BankCardService;
import com.uton.carsokApi.service.JisuAuthService;

/**
 * 银行卡相关
 * @author bing.cheng
 *
 */
@Controller
public class BankCarController {

	private final static Logger logger = Logger.getLogger(BankCarController.class);
	
	@Autowired
	BankCardService bankCardService;
	
	@Autowired
	JisuAuthService jisuAuthService;
	
	/**
	 * 是否绑定银行卡
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/isBindCard" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult isBindCard(HttpServletRequest request) {
		try {
			return bankCardService.isBindCard(request);
		} catch (Exception e) {
			logger.error("isBindCard error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 绑定银行卡
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/bindBankCard" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult bindBankCard(HttpServletRequest request, @RequestBody BindBankCardRequest vo) {
		try {
			return bankCardService.bindBankCard(request, vo);
		} catch (Exception e) {
			logger.error("bindBankCard error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 删除银行卡
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/delBankCard" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult delBankCard(HttpServletRequest request) {
		try {
			return bankCardService.delBankCard(request);
		} catch (Exception e) {
			logger.error("delBankCard error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	
	/**
	 *  获取银行卡信息
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/getBankCardInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getBankCardInfo(HttpServletRequest request) {
		try {
			return bankCardService.getBankCardInfo(request);
		} catch (Exception e) {
			logger.error("getBankCardInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
}
