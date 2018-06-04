package com.uton.carsokApi.pay.weixin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.uton.carsokApi.pay.weixin.utils.MD5Util;

public class PayCommonUtil {
	// 微信参数配置
	public static String API_KEY = "vXxKGu89Oq6huDGpf0bcJDXnjSAA93lx";
	public static String APPID = "wxd5f283425f6f6162";
	public static String MCH_ID = "1442275302";
	
	public static String API_KEY_ZS = "0200bf51a7075f5b5c770ffa118201df";
	public static String APPID_ZS = "wx18e35319d4b0cd3b";
	public static String MCH_ID_ZS = "1486179032";
	
	//public static String notify_url = "http://carsok.utonw.com/carsokApi/uPay/wxpay/notify.do";
	public static String notify_url = "http://carsok.utonw.com/carsokApi/uPay/wxpay/notify.do";
	public static String sys_notify_url="http://carsapi.utonw.com/uPay/wxpay/sys_notify.do";
	// 随机字符串生成
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 请求xml组装
	public static String getRequestXml(SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key)
					|| "sign".equalsIgnoreCase(key)) {
				sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key
						+ ">");
			} else {
				sb.append("<" + key + ">" + value + "</" + key + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
    //生成签名  
    public static String createSign(String characterEncoding,SortedMap<String,Object> parameters){  
          StringBuffer sb = new StringBuffer();  
          Set es = parameters.entrySet();  
          Iterator it = es.iterator();  
          while(it.hasNext()) {  
              Map.Entry entry = (Map.Entry)it.next();  
              String k = (String)entry.getKey();  
              Object v = entry.getValue();  
              if(null != v && !"".equals(v)  
                      && !"sign".equals(k) && !"key".equals(k)) {  
                  sb.append(k + "=" + v + "&");  
              }  
          }  
          sb.append("key=" + API_KEY);  
          String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();  
          return sign;  
      }

	//生成签名
	public static String createSignZS(String characterEncoding,SortedMap<String,Object> parameters){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v)
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + API_KEY_ZS);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}
    
    //请求方法  
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {  
          try {  
               
              URL url = new URL(requestUrl);  
              HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
              
              conn.setDoOutput(true);  
              conn.setDoInput(true);  
              conn.setUseCaches(false);  
              // 设置请求方式（GET/POST）  
              conn.setRequestMethod(requestMethod);  
              conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");  
              // 当outputStr不为null时向输出流写数据  
              if (null != outputStr) {  
                  OutputStream outputStream = conn.getOutputStream();  
                  // 注意编码格式  
                  outputStream.write(outputStr.getBytes("UTF-8"));  
                  outputStream.close();  
              }  
              // 从输入流读取返回内容  
              InputStream inputStream = conn.getInputStream();  
              InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
              String str = null;  
              StringBuffer buffer = new StringBuffer();  
              while ((str = bufferedReader.readLine()) != null) {  
                  buffer.append(str);  
              }  
              // 释放资源  
              bufferedReader.close();  
              inputStreamReader.close();  
              inputStream.close();  
              inputStream = null;  
              conn.disconnect();  
              return buffer.toString();  
          } catch (ConnectException ce) {  
              System.out.println("连接超时：{}"+ ce);  
          } catch (Exception e) {  
              System.out.println("https请求异常：{}"+ e);  
          }  
          return null;  
      } 
    
    //xml解析  
	public static SortedMap<String, String>  doXMLParse(String xmlStr) throws DocumentException{
		SortedMap<String, String> map = null;
		
		Document document = DocumentHelper.parseText(xmlStr);
		Element root = document.getRootElement();
		List<Element> list = root.elements();
		if(list!=null && list.size()>0){
			map = new TreeMap<String, String>();
			for(Element e : list){
				String k = e.getName();
				String v = e.getText();
				System.out.println(k+" : "+v);
				map.put(k, v);
			}
		}
		return map;
	}
}
