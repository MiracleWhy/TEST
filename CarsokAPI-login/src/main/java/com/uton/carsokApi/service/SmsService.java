package com.uton.carsokApi.service;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.uton.carsokApi.util.SendMessageUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.JisuResult;
import com.uton.carsokApi.constants.Jisuapi;
import com.uton.carsokApi.util.HttpClientUtil;
import com.uton.carsokApi.util.HttpUtils;
import com.uton.carsokApi.util.StringUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
/**
 * 发送短信
 * @author eric
 *
 */
@Service
public class SmsService {
	@Resource
    RedisTemplate redisTemplate;
	
	@Value("${sms.swtch}")
	private String smsswth;
	
	
	private final static Logger logger = Logger.getLogger(SmsService.class);
	
	/**发送短信内容模板*/
	private static String CONTENT = "您的验证码是：@。如非本人操作，请忽略本短信【梧桐汽车】";
	
	public boolean sendSms_aliy(String mobile){
		try{
			if(StringUtil.isEmpty(mobile)){
				logger.error("手机短信发送失败，手机号码不能为空！");
				return false;
			}
			String verifyCode = StringUtil.getRandCode();
			logger.info("验证码：" + verifyCode + "手机");
			if (!smsswth.equals("ON")) {
				redisTemplate.opsForValue().set(mobile, verifyCode, 2, TimeUnit.MINUTES);
				return true;
			}
/*			String responseContent = null;
			Map<String, String> headers = new HashMap<String, String>();
		    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		    headers.put("Authorization", Jisuapi.appcode);
		    Map<String, String> querys = new HashMap<String, String>();
		    querys.put("ParamString", Jisuapi.paramString.replace("@", verifyCode));
		    querys.put("RecNum", mobile);
		    querys.put("SignName", Jisuapi.signname);
		    querys.put("TemplateCode", Jisuapi.templatecode);
		    HttpResponse response = HttpUtils.doGet(Jisuapi.host, Jisuapi.path, Jisuapi.method, headers, querys);
		    HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,"UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
		    System.out.println(responseContent.toString());
		    JSONObject  jasonObject = JSONObject.parseObject(responseContent);
		    Map<String, Object> resp = (Map<String, Object>) jasonObject;
			boolean res = (boolean) resp.get("success");*/

			SendMessageUtil.sendMessage(mobile,"【车商APP】您的验证码是："+verifyCode+"。如非本人操作，请忽略本短信");
			redisTemplate.opsForValue().set(mobile, verifyCode, 2, TimeUnit.MINUTES);
		}catch(Exception e){
			logger.error("发送验证码异常", e);
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * 发送短信
	 * @param mobile
	 * @return
	 */
	public boolean sendSms(String mobile){
		try{
			if(StringUtil.isEmpty(mobile)){
				logger.error("手机短信发送失败，手机号码不能为空！");
				return false;
			}
			String verifyCode = StringUtil.getRandCode();
			logger.info("验证码：" + verifyCode + "手机");
			if (!smsswth.equals("ON")) {
				redisTemplate.opsForValue().set(mobile, verifyCode, 2, TimeUnit.MINUTES);
				return true;
			}
			//6位验证码
			
			String content = CONTENT.replace("@", verifyCode);;
			String url = createUrl(mobile,content);
			String result = HttpClientUtil.sendGetRequest(url, "utf-8");
			//判断是否发送成功
			//该方法是 对单个手机号码发送的接口
			if(!StringUtil.isEmpty(result.trim())){
				JisuResult smsresult = (JisuResult) JSONObject.parseObject(result,JisuResult.class);
				if(!smsresult.getMsg().equals("ok")){
					logger.info("手机短信发发送过程中失败！，信息：url:"+url);
					return false;
				}
				//缓存短信验证码
				redisTemplate.opsForValue().set(mobile, verifyCode, 2, TimeUnit.MINUTES);
				//发送成功打印日志
				printSendSmsInfo(mobile,CONTENT);
			}else{
				logger.info("手机短信发发送过程中失败！，信息：url:"+url);
				return false;
			}
		}catch(Exception e){
			logger.error("发送验证码异常", e);
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @Title: createUrl
	 * @Description: 创建生成url
	 * @return
	 */
	private static  String createUrl(String phone,String content) {
		StringBuffer sb = new StringBuffer();
		if (!Jisuapi.SMS_URL.endsWith("?")) {
			Jisuapi.SMS_URL = Jisuapi.SMS_URL.concat("?");
		}
		sb.append(Jisuapi.SMS_URL);
		
		//发送到指定的手机号
		sb.append("mobile").append("=").append(phone);
		sb.append(Jisuapi.PM_SIPLIT);
		
		//发送的内容
		String c = content;
		try {
			c = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append("content").append("=").append(c);
		sb.append(Jisuapi.PM_SIPLIT);
		sb.append("appkey").append("=").append(Jisuapi.APPKEY);
		return sb.toString();
	}
	
	private static void printSendSmsInfo(String mobiles,String msg){
		//发送手机号码
		String[] mobileAry=mobiles.split(",");
		//日志
		StringBuilder logBuilder=new StringBuilder();
		logBuilder.append("********************************发送短信日志***************************************\n");
		
		//显示手机号
		for(int i=0;i<mobileAry.length;i++){
			logBuilder.append(mobileAry[i]+"      ");
			if((i+1)%5==0){
				logBuilder.append("\n");
			}
		}
		//发送短信内容
		logBuilder.append("\n短信内容：\n"+msg);
		logger.info(logBuilder.toString());
	}
	public static void main(String[] args) {
	    String host = "http://sms.market.alicloudapi.com";
	    String path = "/singleSendSms";
	    String method = "GET";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE "+Jisuapi.appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("ParamString", Jisuapi.paramString.replace("@", "123456"));
	    querys.put("RecNum", "18801171093");
	    querys.put("SignName", Jisuapi.signname);
	    querys.put("TemplateCode", Jisuapi.templatecode);
	    String responseContent = null;

	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	//System.out.println(response.getEntity().toString());
	    	//获取response的body
	    	HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,"UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
		    System.out.println(responseContent.toString());
		    JSONObject  jasonObject = JSONObject.parseObject(responseContent);
		    Map<String, Object> resp = (Map<String, Object>) jasonObject;
			boolean res = (boolean) resp.get("success");
			if(res){
				System.out.println(111);
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
