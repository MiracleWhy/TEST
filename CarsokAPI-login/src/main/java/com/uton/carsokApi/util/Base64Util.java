package com.uton.carsokApi.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("restriction")
public class Base64Util {
	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;

		// 读取图片字节数组
		try {
		InputStream in = new FileInputStream(imgFilePath);
		data = new byte[in.available()];
		in.read(data);
		in.close();
		} catch (IOException e) {
		e.printStackTrace();
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		String img = encoder.encode(data);
		return img;// 返回Base64编码过的字节数组字符串
		}
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片  
		if (imgStr == null) // 图像数据为空  
		return false;  
		BASE64Decoder decoder = new BASE64Decoder();  
		try {  
		// Base64解码  
		byte[] bytes = decoder.decodeBuffer(imgStr);  
		for (int i = 0; i < bytes.length; ++i) {  
		if (bytes[i] < 0) {// 调整异常数据  
		bytes[i] += 256;  
		}  
		}  
		// 生成jpeg图片  
		OutputStream out = new FileOutputStream(imgFilePath);  
		out.write(bytes);  
		out.flush();  
		out.close();  
		return true;  
		} catch (Exception e) {  
		return false;  
		}  
	}
	//灰化图片
	public static void grayImage(String sFile,String toFile){
		try{
			File file = new File(sFile);
			BufferedImage image = ImageIO.read(file);
	
			int width = image.getWidth();
			int height = image.getHeight();
	
			BufferedImage grayImage = new BufferedImage(width, height,
					BufferedImage.TYPE_BYTE_GRAY);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int rgb = image.getRGB(i, j);
					grayImage.setRGB(i, j, rgb);
				}
			}
			File newFile = new File(toFile);
			ImageIO.write(grayImage, "jpg", newFile);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type",
	                        "application/x-www-form-urlencoded");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "d6e0f0dbe8c1a5a021a8aac1a0fed10b");
	        connection.setDoOutput(true);
	        connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	public static void main(String[] args) {
		// String strImg = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDABMNDxEPDBMREBEWFRMXHTAfHRsbHTsqLSMwRj5KSUU+RENNV29eTVJpU0NEYYRiaXN3fX59S12Jkoh5kW96fXj/2wBDARUWFh0ZHTkfHzl4UERQeHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHj/wAARCAAfACEDAREAAhEBAxEB/8QAGAABAQEBAQAAAAAAAAAAAAAAAAQDBQb/xAAjEAACAgICAgEFAAAAAAAAAAABAgADBBESIRMxBSIyQXGB/8QAFAEBAAAAAAAAAAAAAAAAAAAAAP/EABQRAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwEAAhEDEQA/APawEBAQEBAgy8i8ZTVV3UY6V1eU2XoWDDZB19S646Gz39w9fkKsW1r8Wm2yo1PYis1be0JG9H9QNYCAgc35Cl3yuVuJZl0cB41rZQa32dt2y6OuOiOxo61vsLcVblxaVyXD3hFFjL6La7I/sDWAgICAgICB/9k=" ;
		// GenerateImage(Base64Util.GetImageStr("D:\\00.jpg").replaceAll("\r\n",""), "D:\\test.jpg");  
		 String imgstr = Base64Util.GetImageStr("C:\\Users\\Administrator\\Desktop\\tupian\\微信图片_20170608120609.jpg");
		 System.out.println(imgstr);
		/* String filepath="C:\\Users\\Administrator\\Desktop\\tupian\\微信图片_20170608120609.jpg";
		String strImg = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ"+
"bWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdp"+
"bj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6"+
"eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1"+
"Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJo"+
"dHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlw"+
"dGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAv"+
"IiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RS"+
"ZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpD"+
"cmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKFdpbmRvd3MpIiB4bXBNTTpJbnN0"+
"YW5jZUlEPSJ4bXAuaWlkOjRBRkVDRkQ0MEQxRjExRTdBNkRBOTUwM0JENUM2RThFIiB4bXBNTTpE"+
"b2N1bWVudElEPSJ4bXAuZGlkOjRBRkVDRkQ1MEQxRjExRTdBNkRBOTUwM0JENUM2RThFIj4gPHht"+
"cE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6NEFGRUNGRDIwRDFGMTFF"+
"N0E2REE5NTAzQkQ1QzZFOEUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NEFGRUNGRDMwRDFG"+
"MTFFN0E2REE5NTAzQkQ1QzZFOEUiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94"+
"OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7fX4U2AAAAa0lEQVR42mL8//8/AzaQZqQDorYC"+
"MUiBz6xzV7CqY2HAD/gIyDMwMVAIhoEBLNDQJghwqQPFwhYglsChTx1Kn8Eh/wLkBUYKfMAMcoE3"+
"HgWHobQtLgWMBFIi3ABcKXE0IRHOjR8JRTNAgAEAEEYVofREjyEAAAAASUVORK5CYII=";
		 GenerateImage(strImg,filepath);*/
		//System.out.print(imgstr.replaceAll("\r\n",""));
		 
	}
	
}
