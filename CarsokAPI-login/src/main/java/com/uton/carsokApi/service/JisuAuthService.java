package com.uton.carsokApi.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.JisuResult;
import com.uton.carsokApi.constants.Jisuapi;
import com.uton.carsokApi.pay.weixin.PayConfig;
import com.uton.carsokApi.util.HttpClientUtil;
import com.uton.carsokApi.util.StringUtil;

/**
 * 极速认证
 * @author eric
 *
 */
@Service
public class JisuAuthService {
	private final static Logger logger = Logger.getLogger(JisuAuthService.class);
	
	/**
	 * 身份证实名认证
	 * @param idcard
	 * @param realname
	 * @return
	 */
	public boolean idcardverify(String idcard,String realname){
		try{
			if(StringUtil.isEmpty(idcard) && StringUtil.isEmpty(realname)){
				logger.info("参数不能为空");
				return false;
			}
			StringBuffer sb = new StringBuffer();
			if (!Jisuapi.IDCARDVERIFY_URL.endsWith("?")) {
				Jisuapi.IDCARDVERIFY_URL = Jisuapi.IDCARDVERIFY_URL.concat("?");
			}
			sb.append(Jisuapi.IDCARDVERIFY_URL);
			
			sb.append("idcard").append("=").append(idcard);
			sb.append(Jisuapi.PM_SIPLIT);
			
			realname = URLEncoder.encode(realname, "UTF-8");
			sb.append("realname").append("=").append(realname);
			sb.append(Jisuapi.PM_SIPLIT);
			
			sb.append("appkey").append("=").append(Jisuapi.APPKEY);
			
			//请求地址
			String url = sb.toString();
			String result = HttpClientUtil.sendGetRequest(url, "utf-8");
			//判断是否发送成功
			if(!StringUtil.isEmpty(result.trim())){
				JisuResult smsresult = (JisuResult) JSONObject.parseObject(result,JisuResult.class);
				if(!smsresult.getMsg().equals("ok")){
					logger.info("实名认证失败，信息：url:"+url);
					return false;
				}
			}else{
				logger.info("实名认证失败，信息：url:"+url);
				return false;
			}
		}catch(Exception e){
			logger.error("idcardverify exception , idcard = " + idcard + "realname = " + realname, e);
			return false;
		}
		return true;
	}
	
	
	/**
	 * 银行卡实名校验
	 * @param idcard
	 * @param realname
	 * @param bankcard
	 * @return
	 */
	public boolean bankcardverify(String idcard,String realname,String bankcard){
		try{
			if(StringUtil.isEmpty(idcard) && StringUtil.isEmpty(realname) && StringUtil.isEmpty(bankcard)){
				logger.info("参数不能为空");
				return false;
			}
			StringBuffer sb = new StringBuffer();
			if (!Jisuapi.BANKCARDVERIFY_URL.endsWith("?")) {
				Jisuapi.BANKCARDVERIFY_URL = Jisuapi.BANKCARDVERIFY_URL.concat("?");
			}
			sb.append(Jisuapi.BANKCARDVERIFY_URL);
			
			sb.append("idcard").append("=").append(idcard);
			sb.append(Jisuapi.PM_SIPLIT);
			
			realname = URLEncoder.encode(realname, "UTF-8");
			sb.append("realname").append("=").append(realname);
			sb.append(Jisuapi.PM_SIPLIT);
			
			sb.append("bankcard").append("=").append(bankcard);
			sb.append(Jisuapi.PM_SIPLIT);
			
			sb.append("appkey").append("=").append(Jisuapi.APPKEY);
			
			//请求地址
			String url = sb.toString();
			String result = HttpClientUtil.sendGetRequest(url, "utf-8");
			//判断是否发送成功
			if(!StringUtil.isEmpty(result.trim())){
				JisuResult jisuresult = (JisuResult) JSONObject.parseObject(result,JisuResult.class);
				if(!jisuresult.getMsg().equals("ok")){
					logger.info("银行卡实名认证失败，信息：url:"+url);
					return false;
				}
			}else{
				logger.info("银行卡实名认证失败，信息：url:"+url);
				return false;
			}
		}catch(Exception e){
			logger.error("bankcardverify exception , idcard = " + idcard + "realname = " + realname + "bankcard =  " + bankcard, e);
			return false;
		}
		return true;
	}
	/**
	 * 博朗订单业务
	 * @param orderNum
	 * @param retMsg
	 * @param retCode
	 */
	public void sendMsgToBolang(String orderNum,String retMsg,String retCode){
		try {
			StringBuffer sb = new StringBuffer();
			if (!PayConfig.bolang_notify_url.endsWith("?")) {
				PayConfig.bolang_notify_url = PayConfig.bolang_notify_url.concat("?");
			}
			sb.append(PayConfig.bolang_notify_url);
			sb.append("orderNum").append("=").append(orderNum);
			sb.append(Jisuapi.PM_SIPLIT);
			retMsg = URLEncoder.encode(retMsg, "UTF-8");
			sb.append("retMsg").append("=").append(retMsg);
			sb.append(Jisuapi.PM_SIPLIT);
			sb.append("retCode").append("=").append(retCode);
			//请求地址
			String url = sb.toString();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			HttpResponse httpResponse = client.execute(post);
			String result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
			logger.info("博朗订单响应:"+result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
