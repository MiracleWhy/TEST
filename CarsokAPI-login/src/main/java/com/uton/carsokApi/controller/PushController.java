package com.uton.carsokApi.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.uton.carsokApi.controller.request.ShareMomentRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.PushRequest;
import com.uton.carsokApi.controller.request.WxPayRequest;
import com.uton.carsokApi.service.PushService;
import com.uton.carsokApi.util.StringUtil;

import java.util.Date;

/**
 * 消息推送
 * @author Administrator
 *
 */
@Controller
public class PushController {
	private final static Logger logger = Logger.getLogger(PushController.class);
	
	@Resource
	PushService pushService;
	
	
	@RequestMapping(value = { "/pushMessage" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult pushMessage(HttpServletRequest request) {
		try {
			String mobile = request.getParameter("mobile");
			String content = request.getParameter("content");
			if(StringUtil.isEmpty(mobile) || StringUtil.isEmpty(content)){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
			}
			boolean df = pushService.SendCustomizedCast(mobile,content,"");
			logger.info("----------推送:推送标识: "+mobile+", 时间: "+new Date()+", 发送是否成功: "+df+" ----------");
			return BaseResult.success();
		} catch (Exception e) {
			logger.error("pushMessage error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 分享次数追加
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/shareMomentCount" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult shareMomentCount(HttpServletRequest request, @Valid @RequestBody ShareMomentRequest vo, BindingResult result) {
		try {
			if (result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return pushService.shareMomentCount(vo);
		}catch (Exception e){
			logger.error(e.getMessage());
			return BaseResult.exception(e.getMessage());
		}
	}
}
