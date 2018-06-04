package com.uton.carsokApi.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import com.alipay.api.AlipayApiException;
import com.uton.carsokApi.pay.alipay.AliPay;
import com.uton.carsokApi.pay.weixin.WeixinPay;

public class PayTool {
	
	/**
	 * 微信支付统一下单
	 * @param map
	 * body   商品描述 车商App-4s保养记录
	 * out_trade_no 商户订单号
	 * total_fee 总金额 
	 * spbill_create_ip 终端ip
	 * @return
	 */
	public static Map<String, String> toPay(Map<String,Object> map){
		Map<String,String> returnMap = null;
		try {
			AliPay pay = new AliPay();
			returnMap = pay.aliAppPay(setSortedMap(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnMap;
	}
	
	public static SortedMap<String, Object> wxPay(String sn,BigDecimal totalAmount,HttpServletRequest request) throws DocumentException{
		SortedMap<String, Object> restMap = null;
		if(StringUtils.isEmpty(request.getParameter("appName"))){
			restMap = WeixinPay.weixinPrePay(sn, totalAmount, request);
		}
		else if(StringUtils.equals(request.getParameter("appName"), "ZHONG_SHENG_APP")){
			restMap = WeixinPay.weixinPrePayZS(sn, totalAmount, request);
		}

		return restMap;
	}

	public static SortedMap<String, Object> wxPay(String sn,BigDecimal totalAmount,HttpServletRequest request,String appName) throws DocumentException{
		SortedMap<String, Object> restMap = null;
		if(StringUtils.isEmpty(appName)){
			restMap = WeixinPay.weixinPrePay(sn, totalAmount, request);
		}
		else if(StringUtils.equals(appName, "ZHONG_SHENG_APP")){
			restMap = WeixinPay.weixinPrePayZS(sn, totalAmount, request);
		}
		return restMap;
	}

	public static SortedMap<String, Object> wxPay(String sn,BigDecimal totalAmount,String ip,String body,String notify_url,String appName) throws DocumentException{
		SortedMap<String, Object> restMap = null;
		if(StringUtils.isEmpty(appName)){
			restMap = WeixinPay.weixinPrePay(sn, totalAmount,ip,body,notify_url);
		}
		else if(StringUtils.equals(appName,"ZHONG_SHENG_APP")){
			restMap = WeixinPay.weixinPrePayZS(sn, totalAmount,ip,body,notify_url);
		}
		return restMap;
	}
	private static SortedMap<String, String> setSortedMap(Map<String, Object> map){
		SortedMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("subject", map.get("body").toString());
		paramMap.put("out_trade_no", map.get("out_trade_no").toString());
		paramMap.put("total_amount", map.get("total_fee").toString());
		paramMap.put("notify_url", map.get("notify_url").toString());
		return paramMap;
	}
	
	public static Map<String, String> notifyPay(HttpServletRequest request){
		Map<String,String> returnMap = null;
		AliPay pay = new AliPay();
		try {
			returnMap = pay.aliNotifyPay(request);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return returnMap;
	}
	
	public static Map<String, String> wxnotifyPay(HttpServletRequest request){
		Map<String,String> returnMap = null;
		WeixinPay pay = new WeixinPay();
		try {
			returnMap = pay.wxNotifyPay(request);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnMap;
	}
}
