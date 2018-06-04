package com.uton.carsokApi.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.service.SubUserService;

/**
 *  子账户相关
 * @author bing.cheng
 *
 */
@Controller
public class SubUserController {
	
	private final static Logger logger = Logger.getLogger(UserInfoController.class);
	
	@Autowired
	SubUserService SubUserService;
	@Resource
	CacheService cacheService;
	
	/**
	 *  修改主账号密钥
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/upSubLoginKey" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult upSubLoginKey(HttpServletRequest request, @RequestBody UpSubAcountKeyRequest vo) {
		try {
			return SubUserService.upSubLoginKey(request, vo);
		} catch (Exception e) {
			logger.error("upSubLoginKey error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 子账户登录
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/subLogin" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult subLogin(HttpServletRequest request, @RequestBody SubLoginRequest vo) {
		try {
			return SubUserService.subLogin(request, vo);
		} catch (Exception e) {
			logger.error("subLogin error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 新增子账户
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/addSubUser" }, method = { RequestMethod.POST })
	public @ResponseBody synchronized BaseResult addSubUser(HttpServletRequest request, @RequestBody AddSubUserRequest vo) {
		try {
			return SubUserService.addSubUser(request, vo);
		} catch (Exception e) {
			logger.error("addSubUser error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 修改子账户
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/upSubUser" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult upSubUser(HttpServletRequest request, @RequestBody UpdateSubUserRequest vo) {
		try {
			return SubUserService.upSubUser(request, vo);
		} catch (Exception e) {
			logger.error("upSubUser error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 删除子账户
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/delSubUser" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult delSubUser(HttpServletRequest request, @RequestBody DelSubUserRequest vo) {
		try {
			return SubUserService.delSubUser(request, vo);
		} catch (Exception e) {
			logger.error("delSubUser error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 子账户列表
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/subUserList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult subUserList(HttpServletRequest request) {
		try {
			return SubUserService.subUserList(request);
		} catch (Exception e) {
			logger.error("subUserList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 子账户列表
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/subUserRoleList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult subUserRoleList(HttpServletRequest request,@Valid @RequestBody ToDoTaskRequest vo,BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return SubUserService.selectListByRole(request,vo);
		} catch (Exception e) {
			logger.error("subUserList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 子账户列表
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = { "/getRoleList" }, method = { RequestMethod.GET })
	public @ResponseBody BaseResult subUserRoleList(HttpServletRequest request) {
		BaseResult result = BaseResult.success();
		try {
			ChildAccount childAccount=cacheService.getSubAcountInfoFromCache(request);
			result.setData(SubUserService.getRoleList(childAccount.getId()));
		} catch (Exception e) {
			logger.error("getRoleList error:", e);
			return BaseResult.exception(e.getMessage());
		}
		return result;
	}


	/**
	 * 增加子账号，追加唯一性校验
	 * @param request
	 * @param vo
	 */
	@RequestMapping(value = { "/subUserQueryOnlyChk" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult subUserQueryOnlyChk(HttpServletRequest request, @Valid @RequestBody QuerySubUserOnlyChkRequest vo,BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return SubUserService.subUserQueryOnlyChk(request,vo);
		} catch (Exception e) {
			logger.error("subUserQueryOnlyChk error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 查询子账号使用记录
	 * @param request
	 */
	@RequestMapping(value = { "/getSubUserRecords" }, method = { RequestMethod.GET })
	public @ResponseBody BaseResult subUserQueryOnlyChk(HttpServletRequest request) {

		try {
			Acount acount = cacheService.getAcountInfoFromCache(request);
			String childPhone = request.getParameter("mobile");
			return BaseResult.success(SubUserService.selectRecordsByChildPhone(acount.getId().toString(),childPhone));
		} catch (Exception e) {
			logger.error("subUserQueryOnlyChk error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

}
