package com.uton.carsokApi.pay.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import com.uton.carsokApi.pay.weixin.utils.Sha1Util;

/**
 * 微信支付
 * @author nanwei
 *
 */
public class WeixinPay {
	public static Logger logger=LoggerFactory.getLogger(WeixinPay.class);
	/**
	 * 
	 * 微信App统一下单
	 * 用户在App中点击去支付、订单保存成功后调用改方法
	 * @param order
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public SortedMap<String, String> wxAppPrePay(SortedMap<String, String> order) throws ClientProtocolException, IOException, DocumentException{
		order.put("appid", PayConfig.appid); //微信开放平台审核通过的应用APPID
		order.put("device_info", PayConfig.device_info); //终端设备号
		order.put("trade_type",PayConfig.trade_type);   //支付方式
		order.put("nonce_str", Sha1Util.getNonceStr()); //随机字符串
		order.put("notify_url", PayConfig.notify_url); //支付通知地址
		order.put("mch_id", PayConfig.mch_id); //商户号
		order.put("key", PayConfig.key);
		//支付总金额，由元转换为分值单位
		BigDecimal total = new BigDecimal(order.get("total_fee"));
		total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
		total = total.multiply(new BigDecimal(100));
		int total_fee = total.intValue();
		order.put("total_fee", total_fee+"");
		RequestHandler handler = new RequestHandler();
		String sign = handler.createSign(order);//签名
		order.put("sign", sign);
		String xml = handler.parseXML(order);
		
		String prepay_id= handler.doPreOrder(xml);//请求预付，获取返回的预支付交易会话标识

		SortedMap<String,String> appMap = new TreeMap<String, String>();
		appMap.put("appid", order.get("appid"));
		appMap.put("partnerid", order.get("mch_id"));
		appMap.put("prepayid", prepay_id);
		appMap.put("package", "Sign=WXPay");
		appMap.put("noncestr", Sha1Util.getNonceStr());
		appMap.put("timestamp", Sha1Util.getTimeStamp());
		appMap.put("key", PayConfig.key);
		String appSign = handler.createSign(appMap);
		appMap.put("sign", appSign);
		return appMap;
	}
	
	/**
	 * 微信APP支付结果通知
	 * @param xml
	 * @return
	 * 业务结果：return_code,SUCCESS(成功)/FAIL(失败)
	 * 响应内容（微信支付结果通知时，返回该内容）：return_msg  
	 * 
	 * 若SUCCESS，则包含如下内容
	 * 交易号：trade_no
	 * 2个都为SUCCESS，表示已支付成功
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public Map<String, String>  wxNotifyPay(HttpServletRequest request) throws DocumentException, UnsupportedEncodingException, IOException{
		RequestHandler handler = new RequestHandler();
		String xml = handler.getNotifyXML(request);//获取支付结果
		System.out.println("----------"+xml);
		//解析支付结果通知
		SortedMap<String, String> map = handler.doXMLParse(xml);
		//私钥进行解密
		map.put("key", PayConfig.key);
		String sign = map.get("sign");
		String return_code = map.get("return_code");
		String result_code = map.get("result_code");
		String s = handler.createSign(map);//签名
		Map<String, String> resultMap = new HashMap<String, String>();
		if(return_code!=null && "SUCCESS".equals(return_code)
				&& result_code!=null && "SUCCESS".equals(result_code)
				&& sign!=null && s!=null && s.equals(sign)){
			resultMap.put("return_code", return_code);
			resultMap.put("tradeNo", map.get("transaction_id"));
			resultMap.put("payOrderNo", map.get("out_trade_no"));
			resultMap.put("payAmount", map.get("total_fee"));
				String msg = "<xml>"
				+"<return_code><![CDATA[SUCCESS]]></return_code>"
				+"<return_msg><![CDATA[OK]]></return_msg>"
				+"</xml>";
			resultMap.put("return_msg", msg);			
		}else{
			resultMap.put("return_code", "FAIL");
			String msg = "<xml>"
					+"<return_code><![CDATA[FAIL]]></return_code>"
					+"<return_msg><![CDATA[SIGNERROR]]></return_msg>"
					+"</xml>";
			resultMap.put("return_msg", msg);		
		}
		return resultMap;		
	}
	
	/**
	 * 
	 * @param sn
	 * @param totalAmount
	 * @param description
	 * @param request
	 * @return
	 * @throws DocumentException
	 */
	public static SortedMap<String, Object> weixinPrePay(String sn,BigDecimal totalAmount,HttpServletRequest request) throws DocumentException {
        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();  
        parameterMap.put("appid", PayCommonUtil.APPID);  
        parameterMap.put("mch_id", PayCommonUtil.MCH_ID);  
        parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));  
        parameterMap.put("body","查询4s记录");  
        parameterMap.put("out_trade_no", sn);  
        parameterMap.put("fee_type", "CNY");  
        System.out.println("jiner");  
        BigDecimal total = totalAmount.multiply(new BigDecimal(100));  
        java.text.DecimalFormat df=new java.text.DecimalFormat("0");  
        parameterMap.put("total_fee", df.format(total));  
        System.out.println("jiner2");  
        parameterMap.put("spbill_create_ip", request.getRemoteAddr());  
        parameterMap.put("notify_url", PayCommonUtil.notify_url);  
        parameterMap.put("trade_type", "APP");  
        String sign = PayCommonUtil.createSign("UTF-8", parameterMap);  
        System.out.println("jiner2   ");  
        parameterMap.put("sign", sign);  
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);  
        System.out.println(requestXML);  
        String result = PayCommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);  
        System.out.println(result);  
        Map<String, String> map = null;  
        map = PayCommonUtil.doXMLParse(result); 
              SortedMap<String, Object> resultMap = new TreeMap<String, Object>();  
        resultMap.put("appid", PayCommonUtil.APPID);  
        resultMap.put("partnerid", PayCommonUtil.MCH_ID);  
        resultMap.put("prepayid", map.get("prepay_id"));  
        resultMap.put("package", "Sign=WXPay");  
        resultMap.put("noncestr", PayCommonUtil.getRandomString(32));  
        resultMap.put("timestamp", Sha1Util.getTimeStamp());  
        String sign2 = PayCommonUtil.createSign("UTF-8", resultMap);  
        resultMap.put("sign", sign2);
        return resultMap; 
	}
	public static SortedMap<String, Object> weixinPrePay(String sn,BigDecimal totalAmount,String ip,String body,String notify_url) throws DocumentException {
        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();  
        parameterMap.put("appid", PayCommonUtil.APPID);  
        parameterMap.put("mch_id", PayCommonUtil.MCH_ID);  
        parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));  
        parameterMap.put("body",body);  
        parameterMap.put("out_trade_no", sn);  
        parameterMap.put("fee_type", "CNY");  
        System.out.println("jiner");  
        BigDecimal total = totalAmount.multiply(new BigDecimal(100));  
        java.text.DecimalFormat df=new java.text.DecimalFormat("0");  
        parameterMap.put("total_fee", df.format(total));  
        System.out.println("jiner2");  
        parameterMap.put("spbill_create_ip", ip);  
        parameterMap.put("notify_url", notify_url);  
        parameterMap.put("trade_type", "APP");  
        String sign = PayCommonUtil.createSign("UTF-8", parameterMap);  
        System.out.println("jiner2   ");  
        parameterMap.put("sign", sign);  
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);  
        System.out.println(requestXML);  
        String result = PayCommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);  
        System.out.println(result);  
        Map<String, String> map = null;  
        map = PayCommonUtil.doXMLParse(result); 
        SortedMap<String, Object> resultMap = new TreeMap<String, Object>();  
        resultMap.put("appid", PayCommonUtil.APPID);  
        resultMap.put("partnerid", PayCommonUtil.MCH_ID);  
        resultMap.put("prepayid", map.get("prepay_id"));  
        resultMap.put("package", "Sign=WXPay");  
        resultMap.put("noncestr", PayCommonUtil.getRandomString(32));  
        resultMap.put("timestamp", Sha1Util.getTimeStamp());  
        String sign2 = PayCommonUtil.createSign("UTF-8", resultMap);  
        resultMap.put("sign", sign2);
        return resultMap; 
	}

	public static SortedMap<String, Object> weixinPrePayZS(String sn, BigDecimal totalAmount, String ip, String body,
			String notify_url) throws DocumentException {
	      	SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();  
	        parameterMap.put("appid", PayCommonUtil.APPID_ZS);  
	        parameterMap.put("mch_id", PayCommonUtil.MCH_ID_ZS);  
	        parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));  
	        parameterMap.put("body",body);  
	        parameterMap.put("out_trade_no", sn);  
	        parameterMap.put("fee_type", "CNY");  
	        System.out.println("jiner");  
	        BigDecimal total = totalAmount.multiply(new BigDecimal(100));  
	        java.text.DecimalFormat df=new java.text.DecimalFormat("0");  
	        parameterMap.put("total_fee", df.format(total));  
	        System.out.println("jiner2");  
	        parameterMap.put("spbill_create_ip", ip);  
	        parameterMap.put("notify_url", notify_url);  
	        parameterMap.put("trade_type", "APP");  
	        String sign = PayCommonUtil.createSign("UTF-8", parameterMap);  
	        System.out.println("jiner2   ");  
	        parameterMap.put("sign", sign);  
	        String requestXML = PayCommonUtil.getRequestXml(parameterMap);  
	        System.out.println(requestXML);  
	        String result = PayCommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);  
	        System.out.println(result);  
	        Map<String, String> map = null;  
	        map = PayCommonUtil.doXMLParse(result); 
	        SortedMap<String, Object> resultMap = new TreeMap<String, Object>();  
	        resultMap.put("appid", PayCommonUtil.APPID_ZS);  
	        resultMap.put("partnerid", PayCommonUtil.MCH_ID_ZS);  
	        resultMap.put("prepayid", map.get("prepay_id"));  
	        resultMap.put("package", "Sign=WXPay");  
	        resultMap.put("noncestr", PayCommonUtil.getRandomString(32));  
	        resultMap.put("timestamp", Sha1Util.getTimeStamp());  
	        String sign2 = PayCommonUtil.createSign("UTF-8", resultMap);  
	        resultMap.put("sign", sign2);
	        logger.info("中盛APP--微信支付"+JSON.toJSONString(parameterMap));
	        return resultMap; 
	}

	public static SortedMap<String, Object> weixinPrePayZS(String sn, BigDecimal totalAmount,
			HttpServletRequest request) throws DocumentException {
	      SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();  
	        parameterMap.put("appid", PayCommonUtil.APPID_ZS);  
	        parameterMap.put("mch_id", PayCommonUtil.MCH_ID_ZS);  
	        parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));  
	        parameterMap.put("body","查询4s记录");  
	        parameterMap.put("out_trade_no", sn);  
	        parameterMap.put("fee_type", "CNY");  
	        System.out.println("jiner");  
	        BigDecimal total = totalAmount.multiply(new BigDecimal(100));  
	        java.text.DecimalFormat df=new java.text.DecimalFormat("0");  
	        parameterMap.put("total_fee", df.format(total));  
	        System.out.println("jiner2");  
	        parameterMap.put("spbill_create_ip", request.getRemoteAddr());  
	        parameterMap.put("notify_url", PayCommonUtil.notify_url);  
	        parameterMap.put("trade_type", "APP");  
	        String sign = PayCommonUtil.createSignZS("UTF-8", parameterMap);
	        System.out.println("jiner2   ");  
	        parameterMap.put("sign", sign);  
	        String requestXML = PayCommonUtil.getRequestXml(parameterMap);  
	        System.out.println(requestXML);  
	        String result = PayCommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);  
	        System.out.println(result);  
	        Map<String, String> map = null;  
	        map = PayCommonUtil.doXMLParse(result); 
	              SortedMap<String, Object> resultMap = new TreeMap<String, Object>();  
	        resultMap.put("appid", PayCommonUtil.APPID_ZS);  
	        resultMap.put("partnerid", PayCommonUtil.MCH_ID_ZS);  
	        resultMap.put("prepayid", map.get("prepay_id"));  
	        resultMap.put("package", "Sign=WXPay");  
	        resultMap.put("noncestr", PayCommonUtil.getRandomString(32));  
	        resultMap.put("timestamp", Sha1Util.getTimeStamp());  
	        String sign2 = PayCommonUtil.createSignZS("UTF-8", resultMap);
	        resultMap.put("sign", sign2);
	        return resultMap; 
	}
}
