package com.uton.carsokApi.pay.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.uton.carsokApi.pay.weixin.utils.MD5Util;

public class RequestHandler {
	
	/**
	 * 特殊字符处理
	 * @param src
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String UrlEncode(String src,String charset) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, charset).replace("+", "%20");
	}

	/**
	 * 获取package的签名包
	 * @param packageParams
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String genPackage(SortedMap<String, String> packageParams) throws UnsupportedEncodingException {
		String sign = createSign(packageParams);

		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = packageParams.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append(k + "=" + UrlEncode(v,"UTF-8") + "&");
		}
		// 去掉最后一个&
		String packageValue = sb.append("sign=" + sign).toString();
		//System.out.println("UrlEncode后 packageValue=" + packageValue);
		return packageValue;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @param packageParams
	 * @return
	 */
	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = packageParams.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + packageParams.get("key"));
		System.out.println("md5 sb:" + sb);
		
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		System.out.println("packge签名:" + sign);
		return sign;

	}
	
	/**
	 * 将请求参数解析为xml格式
	 * @param parameters
	 * @return
	 */
	public String parseXML(SortedMap<String, String> parameters) {
	   StringBuffer sb = new StringBuffer();
       sb.append("<xml>");
       Set<Entry<String, String>> es = parameters.entrySet();
       Iterator<Entry<String, String>> it = es.iterator();
       while(it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if(null != v && !"".equals(v) && !"key".equals(k)) {
				sb.append("<" + k +">" + v + "</" + k + ">\n");
			}
       }
       sb.append("</xml>");
       return sb.toString();
	}
	
	
	
	/**
	 * 将响应结果解析为 map
	 * @param xmlStr
	 * @return
	 * @throws DocumentException 
	 */
	public SortedMap<String, String>  doXMLParse(String xmlStr) throws DocumentException{
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
	
	/**
	 * 请求微信服务，生成APP预支付交易单,获取预支付交易会话标识
	 * @param data
	 * @return 
	 *  prepay_id：预支付交易会话标识
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public String doPreOrder(String data) throws ClientProtocolException, IOException, DocumentException{
		String prepay_id = "";
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpPost post = new HttpPost(PayConfig.pre_order_url);
		post.setEntity(new StringEntity(data,"UTF-8"));
		
		HttpResponse httpResponse = client.execute(post);
		String result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		System.out.println(result);
		SortedMap<String, String> map = doXMLParse(result);
		prepay_id = map.get("prepay_id");
		return prepay_id;
	}
	
	public String getNotifyXML(HttpServletRequest request) throws UnsupportedEncodingException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		StringBuffer b = new StringBuffer();
		String line = "";
		while((line=br.readLine())!=null){
			b.append(line);
		}
		return b.toString();
	}

}
