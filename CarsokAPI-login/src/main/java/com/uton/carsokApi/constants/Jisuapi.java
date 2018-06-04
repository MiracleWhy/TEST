package com.uton.carsokApi.constants;

public class Jisuapi {

	
	/**极速appkey*/
	public static final String APPKEY = "f7daebe26dab2e46";
	
	/**参数分隔符*/
	public static final String PM_SIPLIT = "&";
	
	/** 发送短信地址 */
	public static String SMS_URL = "http://api.jisuapi.com/sms/send";


	/**实名认证*/
	public static String IDCARDVERIFY_URL = "http://api.jisuapi.com/idcardverify/verify";
	
	/**银行卡认证*/
	public static String BANKCARDVERIFY_URL = "http://api.jisuapi.com/bankcardverify3/verify";
	
	/**阿里短信认证*/
	public static final String appcode = "APPCODE 5fef0a697d58472b9dadef49f8954ce7";
	
	public static final String host = "http://sms.market.alicloudapi.com";
	
	public static final String path = "/singleSendSms";
	
	public static final String method = "GET";
	
	public static String paramString = "{\"no\":\"@\"}";
	
	public static final String signname = "车商App";
	
	public static final String templatecode = "SMS_44635002";
	
}
