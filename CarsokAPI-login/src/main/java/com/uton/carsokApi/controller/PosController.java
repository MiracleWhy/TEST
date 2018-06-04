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
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.controller.request.OpenPosRequest;
import com.uton.carsokApi.controller.request.UpPosPwdReuqest;
import com.uton.carsokApi.service.PosService;

/**
 * POS相关
 * @author bing.cheng
 *
 */
@Controller
public class PosController {

	private final static Logger logger = Logger.getLogger(PosController.class);
	
	@Autowired
	PosService posService;
	
	/**
	 * 获取pos机信息
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/getPosInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getPosInfo(HttpServletRequest request) {
		try {
			return posService.getPosInfo(request);
		} catch (Exception e) {
			logger.error("getPosInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 领取pos机
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/receivePos" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult receivePos(HttpServletRequest request) {
		try {
			return posService.receivePos(request);
		} catch (Exception e) {
			logger.error("receivePos error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 开通pos机
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/openPos" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult openPos(HttpServletRequest request, @RequestBody OpenPosRequest vo) {
		try {
			return posService.openPos(request, vo);
		} catch (Exception e) {
			logger.error("openPos error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 修改pos 机登录密码
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/upPosPwd" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult upPosPwd(HttpServletRequest request, @RequestBody UpPosPwdReuqest vo) {
		try {
			if (vo.getNewPosLoginPasswd().equals(vo.getPosLoginPasswd())) {
				return BaseResult.fail(UserErrorCode.OldPwdNewPwdErrorCode, UserErrorCode.OldPwdNewPwdErrorfo);
			}
			return posService.upPosPwd(request, vo);
		} catch (Exception e) {
			logger.error("upPosPwd error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	
}
