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
import com.uton.carsokApi.controller.request.AccountAuthRequest;
import com.uton.carsokApi.controller.request.BusinessLicenseRequest;
import com.uton.carsokApi.service.UserInfoService;

/**
 * 用户信息相关
 * @author bing.cheng
 *
 */
@Controller
public class UserInfoController {

	private final static Logger logger = Logger.getLogger(UserInfoController.class);
	
	@Autowired
	UserInfoService userInfoService;
	
	/**
	 * 获用户基本信息
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/getUserInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getUserInfo(HttpServletRequest request) {
		try {
			return userInfoService.getUserInfo(request);
		} catch (Exception e) {
			logger.error("getUserInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 申请商家三无认证
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/applyBusinessLicense" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult applyBusinessLicense(HttpServletRequest request, @RequestBody BusinessLicenseRequest vo) {
		try {
			return userInfoService.applyBusinessLicense(request, vo);
		} catch (Exception e) {
			logger.error("applyBusinessLicense error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 商家认证状态查询
	 */
//	@RequestMapping(value = { "/applyBusinessLicenseIf" }, method = { RequestMethod.POST })
//	public @ResponseBody BaseResult applyBusinessLicenseIf(HttpServletRequest request){
//		try {
//			return userInfoService.applyBusinessLicenseIf(request);
//		} catch (Exception e) {
//			logger.error("applyBusinessLicenseIf error:", e);
//			return BaseResult.exception(e.getMessage());
//		}
//	}
	
	/**
	 * 绑卡 提现 前置条件
	 * 1：绑卡 2：提现
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/checkAccountAuth" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult checkAccountAuth(HttpServletRequest request, @RequestBody AccountAuthRequest vo) {
		try {
			return userInfoService.checkAccountAuth(request, vo);
		} catch (Exception e) {
			logger.error("applyBusinessLicense error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/checkAuth" }, method = { RequestMethod.GET })
	public @ResponseBody BaseResult checkAuth(String mobile) {
		try {
			return userInfoService.isAuth(mobile);
		} catch (Exception e) {
			logger.error("checkAuth error:", e);
			return BaseResult.exception("系统错误");
		}
	}
	
	@RequestMapping(value = { "/checkAccount" }, method = { RequestMethod.GET })
	public @ResponseBody BaseResult checkAccount(String mobile) {
		try {
			return userInfoService.checkAccount(mobile);
		} catch (Exception e) {
			logger.error("checkAuth error:", e);
			return BaseResult.exception("系统错误");
		}
	}

	/**
	 * 商家认证拒绝原因
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/businessRefuse" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult businessRefuse(HttpServletRequest request) {
		try {
			return userInfoService.businessRefuse(request);
		} catch (Exception e) {
			logger.error("businessRefuse error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 获取用户所有信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getUserInfoAll" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getUserInfoAll(HttpServletRequest request) {
		try {
			return userInfoService.getUserInfoAll(request);
		} catch (Exception e) {
			logger.error("getUserInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
}
