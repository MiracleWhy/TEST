package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.model.Acount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.LoginErrorCode;
import com.uton.carsokApi.controller.response.LoginResponse;
import com.uton.carsokApi.service.LoginService;
import com.uton.carsokApi.service.StoreInfoService;
import com.uton.carsokApi.util.StringUtil;

/**
 * 登录注册相关
 */
@Controller
public class LoginController {
	
	private final static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	StoreInfoService storeInfoService;
	/**
	 * 登录
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult login(HttpServletRequest request, @RequestBody LoginRequest vo) {
		try {
			if (null == vo || StringUtil.isEmpty(vo.getAccount()) 
					||StringUtil.isEmpty(vo.getAccountPassword())) {
				logger.error(LoginErrorCode.LOGIN_ACCOUNT_INFO_MESSAGE + "登录信息为： " + JSON.toJSONString(vo), null);
				return BaseResult.fail(LoginErrorCode.LOGIN_ACCOUNT_INFO_CODE, LoginErrorCode.LOGIN_ACCOUNT_INFO_MESSAGE);
			}
			return loginService.login(vo,request);
		} catch (Exception e) {
			logger.error("login error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 是否已经登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/loginIf" }, method = { RequestMethod.POST })
	public  @ResponseBody BaseResult loginIf(HttpServletRequest request) {
		try {
			return loginService.isLogin(request);
		} catch (Exception e) {
			logger.error("loginIf error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 获取验证码
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/reqValidationCode" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult reqValidationCode(HttpServletRequest request, @RequestBody ValidationCodeRequst vo) {
		try {
			if (null == vo || StringUtil.isEmpty(vo.getAccount())) {
				logger.error("获取验证码, 入参错误 ，参数为： " + JSON.toJSONString(vo), null);
				return BaseResult.fail(LoginErrorCode.GET_VALIDATION_CODE, LoginErrorCode.GET_VALIDATION_CODE_MESSAGE);
			}
			return loginService.reqValidationCode(vo);
		} catch (Exception e) {
			logger.error("reqValidationCode error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 注册
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/register" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult register(HttpServletRequest request, @RequestBody RegisterRequest vo) {
		try {
			BaseResult result = loginService.register(vo);
			if(!result.getRetCode().equals("0000")){
				return result;
			}
			LoginResponse resp = (LoginResponse) result.getData();
			//生成商家详情页
			storeInfoService.makeStoreHtml(resp.getId());
			return result;
		} catch (Exception e) {
			logger.error("register error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 忘记密码
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/forget" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult forget(HttpServletRequest request, @RequestBody ForgetRequest vo) {
		try {
			return loginService.forget(vo);
		} catch (Exception e) {
			logger.error("forget error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 忘记密码
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/modifyPassword" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult modifyPassword(HttpServletRequest request, @RequestBody ModifyPwdRequest vo) {
		try {
			return loginService.modifyPassword(vo,request);
		} catch (Exception e) {
			logger.error("modify error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	
	/**
	 * 登出
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/logout" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult logout(HttpServletRequest request) {
		try {
			return loginService.logout(request);
		} catch (Exception e) {
			logger.error("logout error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 检查用户是否存在
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/isAccountExist" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult isAccountExist(HttpServletRequest request, @RequestBody IsAccountExistRequest vo) {
		try {
			return loginService.isAccountExist(vo);
		} catch (Exception e) {
			logger.error("isAccountExist error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	
	/**
	 * 校验验证码 
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/checkCode" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult checkCode(HttpServletRequest request, @RequestBody CheckCodeRequest vo) {
		try {
			return loginService.checkCode(vo);
		} catch (Exception e) {
			logger.error("checkCode error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 子账号登录
	 * @param request
	 * @param vo
	 * @return
	 */
/*	@RequestMapping(value = { "/subLogin" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult subLogin(HttpServletRequest request) {
		try {
			return loginService.subLogin(request);
		} catch (Exception e) {
			logger.error("subLogin error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}*/


	/**
	 * 获取车商昵称和头像
	 */
	@RequestMapping(value = { "/huanxinMsg" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult huanxinMsg(HttpServletRequest request, @RequestBody Acount vo) {
		try {
			return loginService.huanxinMsg(vo);
		} catch (Exception e) {
			logger.error("gethuanxinError:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 客户管理List
	 */
	@RequestMapping(value = { "/ceshi" }, method = { RequestMethod.GET })
	public String ceshi(HttpServletResponse response,HttpServletRequest request, String mobile,String otherId) {
		if(StringUtil.isEmpty(otherId)){
			otherId = "0";
		}
		request.setAttribute("otherId",otherId);
		request.setAttribute("mobile",mobile);
		if(!StringUtil.isEmpty(request.getParameter("select"))){
			request.setAttribute("customerPhone",request.getParameter("select"));
		}
		return "/customList";
	}

	/**
	 * 注册
	 */
	@RequestMapping(value = { "/registerPage" }, method = { RequestMethod.GET })
	public String register(HttpServletResponse response,HttpServletRequest request, String mobile) {
		request.setAttribute("mobile",mobile);
		try
		{

		}
		catch(Exception e)
		{

		}

		return "/register";
	}

	@RequestMapping(value = { "/deleteInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult deleteByUserName(HttpServletRequest request,@RequestBody Acount ac) {
		try {
			return loginService.deleteByUserName(ac.getMobile());
		} catch (Exception e) {
			logger.error("deleteInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

    @RequestMapping(value = { "/updatePasswordByPmsAccountId" }, method = { RequestMethod.POST })
    public @ResponseBody BaseResult updatePasswordByPmsAccountId(HttpServletRequest request,@RequestBody Acount ac) {
        try {
            return loginService.updatePasswordByPmsAccountId(ac.getId());
        } catch (Exception e) {
            logger.error("deleteInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

}
