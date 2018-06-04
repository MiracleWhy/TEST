package com.uton.carsokApi.pay.lakala.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.util.DateUtil;

import net.sf.json.JSONObject;

public class SignUtils {

	public static OperateResult verifySign(String param) {
		String decode = new String(Base64.decode(param));
		JSONObject paramObj = JSONObject.fromObject(decode);
		String params = paramObj.getString("params");
		String sign2 = paramObj.getString("sign");
		String decryptParams;
		boolean verifySign = false;
		JSONObject paramsObj = null;
		OperateResult result = new OperateResult();
		try {
			decryptParams = SecurityFactory.getOperator(SecurityFactory.TYPE_AES).decrypt(Const.AES_KEY, params);
			verifySign = SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).verifySign(decryptParams, sign2,
					KeyTool.loadKeyFromFile(SignUtils.class.getResource("/lkl/lkl_public_key.pem").getFile()));
			paramsObj = JSONObject.fromObject(decryptParams);
			if (paramObj.get("ver") != null) {
				paramsObj.put("ver", paramObj.getString("ver"));
			}
			if (paramObj.get("reqId") != null) {
				paramsObj.put("reqId", paramObj.getString("reqId"));
			}
			if (paramObj.get("noticeCode") != null) {
				paramsObj.put("noticeCode", paramObj.getString("noticeCode"));
			}
			result.setData(com.alibaba.fastjson.JSONObject.toJSON(paramsObj));
			result.setSuccess(verifySign);
			return result;
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return result;
		}
	}

	public static OperateResult sign(Map<String, Object> paramsMap) {
		JSONObject paramJSONObj = JSONObject.fromObject(paramsMap);
		String paramsStr = paramJSONObj.toString();
		// 签名
		String privateKey;
		String sign = null;
		String encryptParam = null;
		OperateResult result = new OperateResult();
		try {
			privateKey = KeyTool
					.loadKeyFromFile(SignUtils.class.getResource("/lkl/lkl_private_key.pem").getFile());
			sign = SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).sign(paramsStr, privateKey);
			encryptParam = SecurityFactory.getOperator(SecurityFactory.TYPE_AES).encrypt(Const.AES_KEY, paramsStr);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("channelCode", LaKaLaConstants.CHANNEL_CODE);
			paramMap.put("params", encryptParam);
			paramMap.put("sign", sign);
			paramMap.put("ver", "1.4");
			if(paramsMap.get("repId")!=null){
				paramMap.put("repId", paramsMap.get("repId").toString());
			}
			String param = Base64.encode(JSONObject.fromObject(paramMap).toString().getBytes());
			result.setData(param);
			if(paramsMap.get(LaKaLaConstants.NO_BASE64)!=null){
				result.setData(paramMap);
			}
			
			result.setSuccess(true);
		
			result.setMessage("签名成功");
			return result;
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return result;
		}
	}

	public static String getRandomNum(Integer length) {
		Random random = new Random();
		String num = "";
		for (int i = 0; i < length; i++) {
			num = num + "" + random.nextInt(9);
		}
		return num;
	}

	public static String getTimestamp() {
		return DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
	}

	public static String getExpireTimestamp() {
		return DateUtil.DateToString(new Date(System.currentTimeMillis() + 60 * 60 * 1000), "yyyyMMddHHmmss");
	}

	public static String getOrderNo() {
		return getTimestamp() + "" + getRandomNum(4);
	}

	public static void main(String[] args) {
		String str = "eyJ2ZXIiOiIxLjQiLCJzaWduIjoiQTlYNDhjWnhneTdhM2plMkt6ejRrbDF5WnFhNEc2eWNOR25Ba1JxRnM2ZlNtZkE0T1d6L3NVV0h4NTgvSEdFeTAxQzhWRUdwSmlwQkd1STJLczB0SXFlNGJPRlVmbWkrV1djVmFjdnUxL1lYZWZYV1JQdnFKUzFkL1c4aVhYc1V4K05BbHlrMHBFYkR6cTRkTXB2RjlnRHVlSGxlQTU2TnpWYlJJMU8reXh3PSIsInBhcmFtcyI6IlM1dG9IQ2tPNGxJaDdzeHFiazd3V09kWDBiV3I4dE5Kang4Rk9pZEVQTVNwMks1dUVpOC9WRWFjQlNCalY4TWJETEdqdkxreXVVOC9cclxuc1p0UnQ2SjZJNEhxYzY5dGw4ZmU0U2RGNjZFemtLSWp1cjVDckVROExJUkljMWJQMjJGeFA2VEFpVmE4aWhlVlEzN2txakZ0SnlOc1xyXG5FbHQzc0twZU1NUHllUlZtY0RPMWhwWFBYREtoSHc0NXAyUVBiYWdrWmFPL0RXdGNTL01wUVVaYVI1V0V2Rm04VUUxcjdLTjNTRkg4XHJcbnJpUVg1bDNQK24zRXo2V0V2TVZyWlFEVEQzT3dFL0xQaXpsc25USjBDOTREdGY4NmdJMmdHOEt4YTROYm9UdXNJc2hFMHFzNVRpeEFcclxuTGUvOUhyQU1XRjhOOEcvYTRvVzViaDdMck1hQjhhY05LdEFwejh6R0xHRCtjQjhLdFh4NFVyT2cxQUhvcXpuMXhLV2daUkVxQ2JlbVxyXG44STJJV3d1QmdCdEFwZ2FZY1NsSUhuVFM4TVFkL3ZQSXNUaURBb0Rqa0oxVENsNHZHQ29KTDdONTlRVEN3NjNmMnhkN0tIbEo2aC9rXHJcbitlQUEzZTR1OVM2Y0V0dXBUeTBUYXN3OHpBbkZkMG5XMlV0aVN3eDViUThRZElyZ1VBMy84WEFCV1l0SWpxOGtsb3h0cnV3eGJUN1RcclxuUkh4RVNpbWYxU0lTbzhxVHBFbTVBNDhGMlA0Tms1Tzd5MVlXVUhiM1VQd0N4VWRkbno1Z2pKRlZtT3NNN3E4LzRic0ltYS9OYlpaZVxyXG5qWXduSjRLNmIyQ2Z2ckwxUGdCTVZxRHFqZ3hoOWoxZyIsImNoYW5uZWxDb2RlIjoiVDQwMDAwMTQifQ==";

		verifySign(str);
	}

	public static String signRealName(String accountRealName) throws UnsupportedEncodingException {
		return Base64.encode(accountRealName.getBytes("UTF-8")).toString();
	}
}
