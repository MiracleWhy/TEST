package com.uton.carsokApi.pay.alipay.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.uton.carsokApi.pay.alipay.PayConfig;

/**
 * 创建时间：2016年11月10日 下午7:09:08
 * 
 * alipay支付
 * 
 * @author andy
 * @version 2.2
 */

public class AlipayUtil {
	// 统一收单交易创建接口
	private static AlipayClient alipayClient = null;

	public static AlipayClient getAlipayClient() {
		if (alipayClient == null) {
			synchronized (AlipayUtil.class) {
				if (null == alipayClient) {
					alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", PayConfig.app_id,
							PayConfig.pri_key, AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8,
							PayConfig.pub_key);
				}
			}
		}
		return alipayClient;
	}
}
