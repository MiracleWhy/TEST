package com.uton.carsokApi.util;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class HttpClient {
	public static void main(String[] args) {
		JSONObject dataRequest = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject carInfo = new JSONObject();
		carInfo.put("address", "车主地址");
		carInfo.put("carColor","颜色");
		carInfo.put("carBrand","品牌");
		carInfo.put("carModel","型号");
		carInfo.put("carSeries","车系");
		carInfo.put("dealerAddress","车商地址");
		carInfo.put("dealer",true);
		carInfo.put("dealerName","车商姓名");
		carInfo.put("dealerPhone","车商联系方式");
		carInfo.put("dealerProvince","车商所在省");
		carInfo.put("exchange",true);
		carInfo.put("phone","手机号");
		carInfo.put("supervisedPrice","指导价格");
		carInfo.put("province","车主省份");
		carInfo.put("productionStatus","车辆状态 是否停产");
		carInfo.put("mileage","公里数");
		carInfo.put("cardDate","上牌日期");
		carInfo.put("carDescrption","车辆描述");
		carInfo.put("otherContactWay","其他联系方式 如微信号");
		carInfo.put("otherDescription","其他车辆描述");
		
		List<JSONObject> carImages = new ArrayList<JSONObject>();
		JSONObject carDataImage = new JSONObject();
		carDataImage.put("url","imageUrl");
		carDataImage.put("name","imageName");
		carDataImage.put("type","imageType");
		JSONObject carDataImage2 = new JSONObject();
		carDataImage2.put("url","imageUrl");
		carDataImage2.put("name","imageName");
		carDataImage2.put("type","imageType");
		carImages.add(carDataImage);
		carImages.add(carDataImage2);
		data.put("carInfo",carInfo);
		data.put("carImages",carImages);
		dataRequest.put("data",data);
		dataRequest.put("partner","test");
		dataRequest.put("token","test");
		System.out.println(JSONObject.toJSONString(dataRequest));
		String result = sendPostRequestByJava("http://112.74.101.138:8090/thirdCarData.do",
				JSONObject.toJSONString(dataRequest));
		System.out.println(result);
	}
	public static String sendPostRequestByJava(String reqURL, String sendData) {
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; // 写
		InputStream in = null; // 读
		int httpStatusCode = 0; // 远程主机响应的HTTP状态码
		try {
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true); // 指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(30000); // 30秒连接超时
			httpURLConnection.setReadTimeout(30000); // 30秒读取超时
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes("UTF-8"));

			// 清空缓冲区,发送数据
			out.flush();

			// 获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();
			in = httpURLConnection.getInputStream();
			byte[] byteDatas = new byte[in.available()];
			in.read(byteDatas);
			return new String(byteDatas,"UTF-8") + "`" + httpStatusCode;
		} catch (Exception e) {
			// LogUtil.getLogger().error(e.getMessage());
			e.printStackTrace();
			return "Failed`" + httpStatusCode;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					// LogUtil.getLogger().error("关闭输出流时发生异常,堆栈信息如下", e);
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					// LogUtil.getLogger().error("关闭输入流时发生异常,堆栈信息如下", e);
					e.printStackTrace();
				}
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}

	public static String sendPostRequestByJavaWithHeader(String reqURL, String sendData,String token) {
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; // 写
		InputStream in = null; // 读
		int httpStatusCode = 0; // 远程主机响应的HTTP状态码
		try {
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true); // 指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(30000); // 30秒连接超时
			httpURLConnection.setReadTimeout(30000); // 30秒读取超时
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			httpURLConnection.setRequestProperty("token",token);
			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes("UTF-8"));

			// 清空缓冲区,发送数据
			out.flush();

			// 获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();
			in = httpURLConnection.getInputStream();
			byte[] byteDatas = new byte[in.available()];
			in.read(byteDatas);
			return new String(byteDatas,"UTF-8") + "`" + httpStatusCode;
		} catch (Exception e) {
			// LogUtil.getLogger().error(e.getMessage());
			e.printStackTrace();
			return "Failed`" + httpStatusCode;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					// LogUtil.getLogger().error("关闭输出流时发生异常,堆栈信息如下", e);
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					// LogUtil.getLogger().error("关闭输入流时发生异常,堆栈信息如下", e);
					e.printStackTrace();
				}
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}
}
