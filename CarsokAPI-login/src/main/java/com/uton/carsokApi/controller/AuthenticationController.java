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
import com.uton.carsokApi.controller.request.IdcardverifyRequest;
import com.uton.carsokApi.controller.request.WechatVerifyRequest;
import com.uton.carsokApi.service.AuthenticationService;

/**
 *  认证相关
 * @author bing.cheng
 *
 */
@Controller
public class AuthenticationController {

	private final static Logger logger = Logger.getLogger(AuthenticationController.class);
	
	@Autowired
	AuthenticationService authenticationService;
	
	/**
	 * 身份证实名认证
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/idcardverify" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult idcardverify(HttpServletRequest request, @RequestBody IdcardverifyRequest vo) {
		try {
			return authenticationService.idcardverify(request, vo);
		} catch (Exception e) {
			logger.error("idcardverify error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 是否进行身份证实名认证
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/isIdcardverify" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult icardverify(HttpServletRequest request) {
		try {
			return authenticationService.isIdcardverify(request);
		} catch (Exception e) {
			logger.error("isIdcardverify error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	/**
	 * 进行微信认证
	 */
	@RequestMapping(value = { "/wechatVerify" }, method = { RequestMethod.POST })
	public  @ResponseBody BaseResult wechatVerify(HttpServletRequest request, @RequestBody WechatVerifyRequest vo){
		try {
			return authenticationService.wechatVerify(request, vo);
		} catch (Exception e) {
			logger.error("wechatVerify error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	
}
