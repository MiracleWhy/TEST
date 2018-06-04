package com.uton.carsokApi.pay.lakala.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.pay.lakala.util.Base64;
import com.uton.carsokApi.pay.lakala.util.Const;
import com.uton.carsokApi.pay.lakala.util.KeyTool;
import com.uton.carsokApi.pay.lakala.util.SecurityFactory;
import com.uton.carsokApi.pay.lakala.util.SignUtils;

import net.sf.json.JSONObject;

public class LaKaLaService {

	public BaseResult payCheck() throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("v","1.0");
		paramsMap.put("merId","TEST");
		paramsMap.put("secId","RSA");
		paramsMap.put("reqId",UUID.randomUUID().toString().replace("-", ""));
		paramsMap.put("orderId", "1234");
		paramsMap.put("amount", new BigDecimal("1.0"));
		paramsMap.put("lakalaQueryTime", new SimpleDateFormat(
				"yyyyMMddHHmmss").format(new Date(System
				.currentTimeMillis())));
		
		JSONObject paramJSONObj = JSONObject.fromObject(paramsMap);
		String paramsStr = paramJSONObj.toString();
		//签名
		String privateKey=KeyTool.loadKeyFromFile(this.getClass().getResource("/lkl/test_lkl_private_key.pem").getFile());
		String sign=SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).sign(paramJSONObj.toString(), privateKey);
		//加密
		String encryptParam=SecurityFactory.getOperator(SecurityFactory.TYPE_AES).encrypt(Const.AES_KEY, paramsStr);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("params", encryptParam);
		paramMap.put("sign",sign);
	
		String param=Base64.encode( JSONObject.fromObject(paramMap).toString().getBytes());
		return null;
	}

	public BaseResult paynotice() throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("v", "1.0");
		paramsMap.put("merId", "TEST");
		paramsMap.put("secId", "RSA");
		paramsMap.put("reqId", UUID.randomUUID().toString().replace("-", ""));
		paramsMap.put("orderId", "1234");
		paramsMap.put("amount", new BigDecimal("1.0"));
		paramsMap.put("amountPay", new BigDecimal("1.0"));
		paramsMap.put("payType", "D");
		paramsMap.put("partnerBillNo", "67890");
		paramsMap.put("sid", UUID.randomUUID().toString().replaceAll("-", ""));
		paramsMap.put("lakalaBillNo", UUID.randomUUID().toString().replaceAll("-", ""));

		paramsMap.put("currency", "156");
		paramsMap.put("lakalaPayTime",
				new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())));
		paramsMap.put("terminalno", "CBC1234567890");
		paramsMap.put("usermobileno", "");
		paramsMap.put("retCode", "0000");
		paramsMap.put("retMsg", "交易成功");

		JSONObject paramJSONObj = JSONObject.fromObject(paramsMap);
		String paramsStr = paramJSONObj.toString();
		// 签名
		String privateKey = KeyTool.loadKeyFromFile(this.getClass().getResource("/lkl/test_lkl_private_key.pem").getFile());
		String sign = SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).sign(paramJSONObj.toString(), privateKey);
		// 加密
		String encryptParam = SecurityFactory.getOperator(SecurityFactory.TYPE_AES).encrypt(Const.AES_KEY, paramsStr);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("params", encryptParam);
		paramMap.put("sign", sign);

		String param = Base64.encode(JSONObject.fromObject(paramMap).toString().getBytes());
		return null;
	}

	public BaseResult makeMember(HttpServletRequest request) throws Exception {
		String channelCode = request.getParameter("channelCode");
		String orderId = request.getParameter("orderId");
		String amount = request.getParameter("amount");
		String userId = request.getParameter("userId");
		String phoneNumber = request.getParameter("phoneNumber");
		String productName = request.getParameter("productName");
		String remark = request.getParameter("remark");
		String productDesc = request.getParameter("productDesc");
		String callbackUrl = request.getParameter("callbackUrl");
		String randnum = request.getParameter("randnum");
		String timestamp = request.getParameter("timestamp");
		String expriredtime = request.getParameter("expriredtime");
		String optCode = request.getParameter("optCode");
		String businessName = request.getParameter("businessName");
		String accountName = request.getParameter("accountName");
		String accountNo = request.getParameter("accountNo");
		String branchBankno = request.getParameter("branchBankno");
		String accountType = request.getParameter("accountType");
		String realName = request.getParameter("realName");
		String ver = request.getParameter("ver");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("orderId", orderId);
		paramsMap.put("amount", new BigDecimal(amount));
		paramsMap.put("userId", userId);
		paramsMap.put("phoneNumber", phoneNumber);
		paramsMap.put("productName", productName);
		paramsMap.put("remark", remark);
		paramsMap.put("productDesc", productDesc);
		paramsMap.put("callbackUrl", callbackUrl);
		paramsMap.put("randnum", randnum);
		paramsMap.put("timestamp", timestamp);
		paramsMap.put("expriredtime", expriredtime);
		paramsMap.put("optCode", optCode);
		paramsMap.put("businessName", businessName);
		paramsMap.put("accountName", accountName);
		paramsMap.put("accountNo", accountNo);
		paramsMap.put("branchBankno", branchBankno);
		paramsMap.put("accountType", accountType);
		paramsMap.put("realName", realName);

		paramsMap.put("ver", ver);

		JSONObject paramJSONObj = JSONObject.fromObject(paramsMap);
		String paramsStr = paramJSONObj.toString();
		// 签名
		String privateKey = KeyTool.loadKeyFromFile(this.getClass().getResource("/test_lkl_private_key.pem").getFile());
		String sign = SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).sign(paramJSONObj.toString(), privateKey);
		// 加密
		String encryptParam = SecurityFactory.getOperator(SecurityFactory.TYPE_AES).encrypt(Const.AES_KEY, paramsStr);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("channelCode", channelCode);
		paramMap.put("params", encryptParam);
		paramMap.put("sign", sign);
		String param = Base64.encode(JSONObject.fromObject(paramMap).toString().getBytes());
		return null;
	}

	public BaseResult makeOrder(HttpServletRequest request) throws Exception {
		String channelCode = request.getParameter("channelCode");
		String orderId = request.getParameter("orderId");
		String amount = request.getParameter("amount");
		String userId = request.getParameter("userId");
		String phoneNumber = request.getParameter("phoneNumber");
		String productName = request.getParameter("productName");
		String remark = request.getParameter("remark");
		String productDesc = request.getParameter("productDesc");
		String callbackUrl = request.getParameter("callbackUrl");
		String randnum = request.getParameter("randnum");
		String timestamp = request.getParameter("timestamp");
		String expriredtime = request.getParameter("expriredtime");
		String optCode = request.getParameter("optCode");
		String ver = request.getParameter("ver");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("orderId", orderId);
		paramsMap.put("amount", new BigDecimal(amount));
		paramsMap.put("userId", userId);
		paramsMap.put("phoneNumber", phoneNumber);
		paramsMap.put("productName", productName);
		paramsMap.put("remark", remark);
		paramsMap.put("productDesc", productDesc);
		paramsMap.put("callbackUrl", callbackUrl);
		paramsMap.put("randnum", randnum);
		paramsMap.put("timestamp", timestamp);
		paramsMap.put("expriredtime", expriredtime);
		paramsMap.put("optCode", optCode);
		paramsMap.put("ver", ver);
		paramsMap.put("sss", "sss");

		JSONObject paramJSONObj = JSONObject.fromObject(paramsMap);
		String paramsStr = paramJSONObj.toString();
		// 签名
		String privateKey = KeyTool.loadKeyFromFile(this.getClass().getResource("/test_lkl_private_key.pem").getFile());
		String sign = SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).sign(paramJSONObj.toString(), privateKey);
		// 加密
		String encryptParam = SecurityFactory.getOperator(SecurityFactory.TYPE_AES).encrypt(Const.AES_KEY, paramsStr);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("channelCode", channelCode);
		paramMap.put("params", encryptParam);
		paramMap.put("sign", sign);
		String param = Base64.encode(JSONObject.fromObject(paramMap).toString().getBytes());
		return null;
	}

	public BaseResult makeOrder2(HttpServletRequest request) throws Exception {
		String channelCode = request.getParameter("channelCode");
		String orderId = request.getParameter("orderId");
		String amount = request.getParameter("amount");
		String userId = request.getParameter("userId");
		String phoneNumber = request.getParameter("phoneNumber");
		String productName = request.getParameter("productName");
		String remark = request.getParameter("remark");
		String productDesc = request.getParameter("productDesc");
		String callbackUrl = request.getParameter("callbackUrl");
		String randnum = request.getParameter("randnum");
		String timestamp = request.getParameter("timestamp");
		String expriredtime = request.getParameter("expriredtime");
		String optCode = request.getParameter("optCode");
		String ver = request.getParameter("ver");
		String srcPartnerOrderNo = request.getParameter("srcPartnerOrderNo");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("orderId", orderId);
		paramsMap.put("amount", new BigDecimal(amount));
		paramsMap.put("userId", userId);
		paramsMap.put("phoneNumber", phoneNumber);
		paramsMap.put("productName", productName);
		paramsMap.put("remark", remark);
		paramsMap.put("productDesc", productDesc);
		paramsMap.put("callbackUrl", callbackUrl);
		paramsMap.put("randnum", randnum);
		paramsMap.put("timestamp", timestamp);
		paramsMap.put("expriredtime", expriredtime);
		paramsMap.put("optCode", optCode);
		paramsMap.put("srcPartnerOrderNo", srcPartnerOrderNo);
		paramsMap.put("ver", ver);
		JSONObject paramJSONObj = JSONObject.fromObject(paramsMap);
		String paramsStr = paramJSONObj.toString();
		// 签名
		String privateKey = KeyTool.loadKeyFromFile(this.getClass().getResource("/test_lkl_private_key.pem").getFile());
		String sign = SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).sign(paramJSONObj.toString(), privateKey);
		// 加密
		String encryptParam = SecurityFactory.getOperator(SecurityFactory.TYPE_AES).encrypt(Const.AES_KEY, paramsStr);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("channelCode", channelCode);
		paramMap.put("params", encryptParam);
		paramMap.put("sign", sign);
		String param = Base64.encode(JSONObject.fromObject(paramMap).toString().getBytes());
		return null;
	}

	public BaseResult partnerResponse(HttpServletRequest request) throws Exception {
		String param = request.getParameter("param");
		String decode = new String(Base64.decode(param));
		JSONObject paramObj = JSONObject.fromObject(decode);
		String params = paramObj.getString("params");
		String sign = paramObj.getString("sign");
		String decryptParams = SecurityFactory.getOperator(SecurityFactory.TYPE_AES).decrypt(Const.AES_KEY, params);
		JSONObject paramsObj = JSONObject.fromObject(decryptParams);

		boolean verifySign = SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).verifySign(decryptParams, sign,
				KeyTool.loadKeyFromFile(this.getClass().getResource("/test_lkl_public_key.pem").getFile()));
		return null;
	}


	public static void main(String[] args) {
		LaKaLaService service=new LaKaLaService();
		try {
			service.payCheck();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
