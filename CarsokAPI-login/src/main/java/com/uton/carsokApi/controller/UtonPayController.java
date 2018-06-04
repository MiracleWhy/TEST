package com.uton.carsokApi.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.uton.carsokApi.util.StringUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.constants.enums.PayWayEnum;
import com.uton.carsokApi.controller.request.WxPayRequest;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.pay.PayTool;
import com.uton.carsokApi.pay.alipay.PayConfig;
import com.uton.carsokApi.service.JisuAuthService;

/**
 * 微信SDK支付
 * 
 * @author nanwei
 *
 */
@Controller
@RequestMapping("/uPay")
public class UtonPayController {
	private final static Logger logger = Logger.getLogger(UtonPayController.class);
	@Autowired
	private JisuAuthService authService;
	@Autowired
	private EventBus eventBus;

	/**
	 * 微信统一下单，返回支付会话标识
	 * 
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/toPay" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult toPay(HttpServletRequest request, @Valid @RequestBody WxPayRequest vo,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo,
						result.getFieldError().getDefaultMessage());
			}
			// 封装请求参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("body", vo.getBody());
			map.put("total_fee", vo.getTotalfee());
			map.put("out_trade_no", vo.getOrderNum());
			map.put("notify_url", PayConfig.notify_url);
			// 请求支付 返回restMap
			Map<String, String> restMap = PayTool.toPay(map);
			if (restMap != null) {
				return BaseResult.success(restMap);
			}
			return BaseResult.fail(ErrorCode.ExceptionRetCode, ErrorCode.ExceptionRetInfo);
		} catch (Exception e) {
			logger.error("getChildRole error:", e);
			return BaseResult.exception(ErrorCode.ExceptionRetInfo);
		}
	}

	@RequestMapping(value = { "/wxPay" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult wxPay(HttpServletRequest request, @Valid @RequestBody WxPayRequest vo,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo,
						result.getFieldError().getDefaultMessage());
			}
			BigDecimal total = new BigDecimal(vo.getTotalfee());
			String appName= StringUtil.isEmpty(vo.getAppName())?"":vo.getAppName();
			SortedMap<String, Object> restMap = PayTool.wxPay(vo.getOrderNum(), total, request,appName);
			if (restMap != null) {
				return BaseResult.success(restMap);
			}
			return BaseResult.fail(ErrorCode.ExceptionRetCode, ErrorCode.ExceptionRetInfo);
		} catch (Exception e) {
			logger.error("getChildRole error:", e);
			return BaseResult.exception(ErrorCode.ExceptionRetInfo);
		}
	}

	/**
	 * 支付宝支付回调
	 * 
	 * @param req
	 * @param resp
	 * @throws AlipayApiException
	 * @throws ClientProtocolException
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = { "/alipay/notify" }, method = { RequestMethod.POST })
	public void aliPayNotify(HttpServletRequest req, HttpServletResponse resp)
			throws AlipayApiException, ClientProtocolException, IOException {
		Map<String, String> map = PayTool.notifyPay(req);
		Map<String, String> respData = new HashMap<>();
		if (map != null) {
			String return_code = map.get("return_code");
			if ("SUCCESS".equals(return_code)) {
				authService.sendMsgToBolang(map.get("payOrderNo"), "支付成功", "0000");
			} else {
				authService.sendMsgToBolang(map.get("payOrderNo"), "支付失败", "0001");
			}
			resp.getWriter().print(map.get("return_msg"));
		}
	}

	@RequestMapping(value = { "/wxpay/notify" }, method = { RequestMethod.POST })
	public void wxpayNotify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String, String> map = PayTool.wxnotifyPay(req);
		Map<String, String> respData = new HashMap<>();
		if (map != null) {
			String return_code = map.get("return_code");
			if ("SUCCESS".equals(return_code)) {
				authService.sendMsgToBolang(map.get("payOrderNo"), "支付成功", "0000");
			} else {
				authService.sendMsgToBolang(map.get("payOrderNo"), "支付失败", "0001");
			}
			resp.getWriter().print(map.get("return_msg"));
		}
	}

	@RequestMapping(value = { "/alipay/sys_notify" }, method = { RequestMethod.POST })
	public void aliPayNotifyForSystem(HttpServletRequest req, HttpServletResponse resp)
			throws AlipayApiException, ClientProtocolException, IOException {
		Map<String, String> map = PayTool.notifyPay(req);
		if (map != null) {
			BaseEvent event = new BaseEvent();
			map.put("payWay", PayWayEnum.ALI.name());
			event.setData(JSONObject.toJSONString(map));
			event.setEventName(EventConstants.PAY_NOTIFY_EVENT);
			event.setWeight("1000");
			eventBus.publish(event);
			resp.getWriter().print(map.get("return_msg"));
		}
	}

	@RequestMapping(value = { "/wxpay/sys_notify" }, method = { RequestMethod.POST })
	public void wxpayNotifyForSystem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String, String> map = PayTool.wxnotifyPay(req);
		if (map != null) {
			map.put("payWay", PayWayEnum.WECHAT.name());
			BaseEvent event = new BaseEvent();
			event.setData(JSONObject.toJSONString(map));
			event.setEventName(EventConstants.PAY_NOTIFY_EVENT);
			event.setWeight("1000");
			eventBus.publish(event);
			resp.getWriter().print(map.get("return_msg"));
		}
	}

}
