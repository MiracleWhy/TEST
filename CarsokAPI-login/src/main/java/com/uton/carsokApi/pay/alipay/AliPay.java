package com.uton.carsokApi.pay.alipay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.uton.carsokApi.pay.alipay.util.PayUtil;
import com.uton.carsokApi.pay.alipay.util.UtilDate;

public class AliPay {
	
	
	/**
	 * aliPay App支付，订单保存成功后调用该方法
	 * @param order
	 * 公共参数
	 * app_id 支付宝分配给开发者的应用ID
	 * method 接口名称
	 * charset 请求使用的编码格式，如utf-8,gbk,gb2312等
	 * sign_type 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
	 * sign 商户请求参数的签名串
	 * timestamp 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
	 * version 调用的接口版本，固定为：1.0
	 * notify_url 异步通知地址
	 * biz_content 业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递
	 * 业务参数
	 * subject 商品标题
	 * out_trade_no 商户订单号 
	 * total_amount 订单金额 单位为元，精确到小数点后两位
	 * product_code 销售产品码，为固定值
	 * @return
	 */
	public Map<String, String> aliAppPay(SortedMap<String, String> order) throws IOException{
		// 支付业务请求参数
		BigDecimal b = new BigDecimal(order.get("total_amount"));
		b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
		order.put("total_amount", b.toString()+"");
		order.put("product_code", PayConfig.product_code);
		String biz_content = JSONObject.toJSONString(order);
		//封装公共请求参数
		SortedMap<String, String> parameter = new TreeMap<String, String>();
		parameter.put("app_id", PayConfig.app_id);
		parameter.put("method", PayConfig.method);
		parameter.put("charset", PayConfig.charset);
		parameter.put("sign_type", PayConfig.sign_type);
		parameter.put("timestamp", UtilDate.getDateFormatter());
		parameter.put("version", PayConfig.version);
		parameter.put("notify_url", order.get("notify_url"));
		parameter.put("biz_content", biz_content);
		//签名
		parameter.put("sign", PayUtil.getSign(parameter, PayConfig.pri_key));
		Map<String, String> payMap = new HashMap<>();
		payMap.put("orderStr", PayUtil.getSignEncodeUrl(parameter, true));
		return payMap;
	}
	
	
	
	/**
	 * aliPay异步通知
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AlipayApiException 
	 */
	public Map<String, String> aliNotifyPay(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException{
		Enumeration<?> pNames = request.getParameterNames();
		Map<String, String> param = new HashMap<String, String>();
		Map<String,String> resultMap = new HashMap<String,String>();
		while (pNames.hasMoreElements()) {
			String pName = (String) pNames.nextElement();
			param.put(pName, request.getParameter(pName));
		}
		System.out.println("out_trade_no = "+param.get("out_trade_no"));
		System.out.println("trade_no = "+param.get("trade_no"));
		String returnCode = param.get("trade_status"); //支付状态
		System.out.println(returnCode);
		//boolean signVerified = AlipaySignature.rsaCheckV1(param, PayConfig.pub_key,AlipayConstants.CHARSET_UTF8); // 校验签名是否正确
		if(returnCode!=null && ("TRADE_SUCCESS".equals(returnCode) || "TRADE_FINISHED".equals(returnCode)) /**&& signVerified**/){//验证成功
			resultMap.put("return_msg", "success");
			resultMap.put("return_code", "SUCCESS");
			resultMap.put("payAmount", param.get("buyer_pay_amount"));
			resultMap.put("payOrderNo", param.get("out_trade_no"));
		}else{
			resultMap.put("return_msg", "fail");
			resultMap.put("return_code", "fail");
			resultMap.put("payOrderNo", param.get("out_trade_no"));
		}
		return resultMap;
	}
}
