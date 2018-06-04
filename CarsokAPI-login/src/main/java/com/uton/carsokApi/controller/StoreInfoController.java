package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;

//import com.uton.carsokApi.controller.request.ModifyLoginAliasRequest;
import com.uton.carsokApi.model.Acount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.StoreInfoRequest;
import com.uton.carsokApi.service.StoreInfoService;

/**
 *  店铺信息相关controller
 * @author bing.cheng
 *
 */
@Controller
public class StoreInfoController {
	
	private final static Logger logger = Logger.getLogger(StoreInfoController.class);
	
	@Autowired
	StoreInfoService storeInfoService;
	
	/**
	 * 获取店铺信息
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/getStoreInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getStoreInfo(HttpServletRequest request) {
		try {
			return storeInfoService.getStoreInfo(request);
		} catch (Exception e) {
			logger.error("getStoreInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 修改店铺信息
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/upStoreInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult upStoreInfo(HttpServletRequest request, @RequestBody StoreInfoRequest vo) {
		try {
			return storeInfoService.upStoreInfo(request, vo);
		} catch (Exception e) {
			logger.error("upStoreInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 获取微店地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getStoreUrl" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getStoreUrl(HttpServletRequest request) {
		try {
			return storeInfoService.getStoreUrl(request);
		} catch (Exception e) {
			logger.error("upStoreInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/refreshStore" }, method = {RequestMethod.POST})
	public @ResponseBody BaseResult refreshStore(HttpServletRequest request) {
		try {
			return storeInfoService.refreshStore(request);
		} catch (Exception e) {
			logger.error("upStoreInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}		
	}

	/**
	 * 修改子账户的信息
	 *
	 * @param request
	 * @param acount
	 * @return
	 */
	@RequestMapping(value = {"/updateMerchantInfo"}, method = {RequestMethod.POST})
	public @ResponseBody
	BaseResult updateMerchantInfo(HttpServletRequest request, @RequestBody Acount acount) {
		try {
			BaseResult result = storeInfoService.updateMerchantInfo(acount);
			return result;
		} catch (Exception e) {
			logger.error("updateMerchantInfo:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

//	/**
//	 * 修改登录名
//	 * @param request
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value = { "/modifyLoginAlias" }, method = { RequestMethod.POST })
//	public @ResponseBody BaseResult modifyLoginAlias(HttpServletRequest request, @RequestBody ModifyLoginAliasRequest vo) {
//		try {
//			return storeInfoService.modifyLoginAlias(request, vo);
//		} catch (Exception e) {
//			logger.error("modifyLoginAlias error:", e);
//			return BaseResult.exception(e.getMessage());
//		}
//	}
}
