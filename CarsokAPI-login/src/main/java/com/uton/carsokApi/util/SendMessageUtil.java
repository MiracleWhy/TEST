package com.uton.carsokApi.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 发送短信
 * @author Administrator
 *
 */
public class SendMessageUtil {

	/**
	 * 
	 * @param sendType 内容类型  1审批 2通知客户类 3通知业务经理
	 * @param telephone 电话号码
	 * @param title 短信签名
	 * @param contentParam 短信内容所需要参数, touser:发送给的客户/用户姓名，orderid:订单编号，contractnum:合同编号
	 * @return
	 */
	public static String sendMessage(String telephone,String title){
		StringBuffer sb = new StringBuffer("");
		try{
			String content = title;

			//SysConfig corpId = sySConfigQueryService.queryByConfigName(SysConfigEnum.MESSAGE_CORP_ID.name());
			//SysConfig password = sySConfigQueryService.queryByConfigName(SysConfigEnum.MESSAGE_PASSWORD.name());
			String parm = "CorpID=" + "xczy" + "&Pwd=" + "thefirst"+ "&Mobile=" + telephone+"&Content=" + URLEncoder.encode(content, "UTF-8");

			URL url = new URL("http://101.200.29.88:8082/SendMT/SendMessage");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			connection.connect();

			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			
			out.writeBytes(parm.toString());
			out.flush();
			out.close();
			
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sb.toString();
	} 
	
	public static void main(String[] args){
		//Map<String,Object> map = new HashMap<>();
		//map.put("orderId", "123123123123");
		//map.put("taskName", "逾期处理");
		//System.out.println(sendMessage(1,"15140324874","e车圈",map));
	}	
}
