package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.controller.request.LKLApplyRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.pay.lakala.service.LKLService;
import com.uton.carsokApi.pay.lakala.util.SignUtils;
import com.uton.carsokApi.service.UploadService;

@Controller
@RequestMapping("/lkl")
public class LaKaLaController {

	@Autowired
	private LKLService lklService;
	@Autowired
	private UploadService uploadService;
	
	public static Logger logger = Logger.getLogger(OrderController.class);

	@RequestMapping("/payCheck")
	@ResponseBody
	public Object payCheck(HttpServletRequest request) {
		String param = request.getParameter("param");
		logger.info("拉卡拉接口:param"+param);
		OperateResult result = null;
		try {
			result = SignUtils.verifySign(param);
			if (result.isSuccess()) {
				result = lklService.payCheck((JSONObject) result.getData());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("拉卡拉接口:"+e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
		return result.getData();
	}

	@RequestMapping("/applyNotify")
	@ResponseBody
	public Object applyPosNotify(HttpServletRequest request) {
		String param = request.getParameter("param");
		logger.info("拉卡拉接口:param"+param);
		OperateResult result = null;
		try {
			result = SignUtils.verifySign(param);
			if (result.isSuccess()) {
				result = lklService.applyNotify((JSONObject) result.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error("拉卡拉接口:"+e.getMessage());
			return e.getMessage();
		}

		return result.getData();
	}

	@RequestMapping("/payNotify")
	@ResponseBody
	public Object payNotify(HttpServletRequest request) {
		String param = request.getParameter("param");
		logger.info("拉卡拉接口:param"+param);
		OperateResult result = null;
		try {
			result = SignUtils.verifySign(param);
			if (result.isSuccess()) {
				result = lklService.payNotify((JSONObject) result.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉接口:"+e.getMessage());
			return e.getMessage();
		}
		return result.getData();
	}

	@RequestMapping("getSignAccountInfo")
	@ResponseBody
	public OperateResult getOpenAccountSignInfo(HttpServletRequest request) {
		OperateResult result = null;
		try {
			result = lklService.openPos(request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉接口:"+e.getMessage());
			result=new OperateResult(false, "网络异常,请稍后重试");
		}
		return result;
	}

	@RequestMapping("checkApplyLkl")
	@ResponseBody
	public OperateResult checkAndApplyLkl(HttpServletRequest request) {
		/**
		 * 是否开通拉卡拉
		 */
		OperateResult result = new OperateResult();
		try {
			result = lklService.checkAndApplyLkl(request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉接口:"+e.getMessage());
			result.setMessage("网络异常,请稍后重试。");
			return result;
		}
		return result;
	}
	@RequestMapping("apply")
	@ResponseBody
	public OperateResult lklApply(HttpServletRequest request, @RequestBody LKLApplyRequest lklRequest){
		logger.info("拉卡拉接口:param"+JSON.toJSONString(lklRequest));
		/**
		 * 创建订单创建流水
		 */
		OperateResult result = new OperateResult();
		try {
			result = lklService.lklApplyHandler(request,lklRequest);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉接口:"+e.getMessage());
			result.setMessage("网络异常,请稍后重试。");
			return result;
		}
		return result;
	}
	


}
