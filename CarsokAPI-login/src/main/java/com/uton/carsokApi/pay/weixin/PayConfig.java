package com.uton.carsokApi.pay.weixin;

/**
 * 微信支付静态配置
 * @author nanwei
 *
 */
public class PayConfig {
	
	/** WeChatPay私钥 **/
	public static final String key = "vXxKGu89Oq6huDGpf0bcJDXnjSAA93lx";
	
	/** 应用appid **/
	public static final String appid = "wxd5f283425f6f6162";
	
	/** 终端设备号 默认WEB **/
	public static final String device_info = "WEB";
	
	/** 交易类型 **/
	public static final String trade_type = "APP";
	
	/** 商户号 **/
	public static final String mch_id = "";
	
	public static final String pre_order_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static final String notify_url = "http://carsok.utonw.com/carsokApi/uPay/wchatpay/notify.do";
	
	public static final String sys_notify_url = "http://carsok.utonw.com/carsokApi/uPay/wxpay/sys_notify.do";
	
	public static String bolang_notify_url = "http://bolang.91echedai.com/utonCarPayNotify";
	
}
