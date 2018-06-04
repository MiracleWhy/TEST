package com.uton.carsokApi.service;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.util.StringUtil;
import com.uton.carsokApi.util.UploadUtil;


@Service
public class UploadService {
	//图片服务器地址
	@Value("${pic.host}")
	private String pichost;
	
	//临时存储地址
	@Value("${temporary.dir}")
	private String temporary_dir;
	
	public String upload(HttpServletRequest request,String imgstr) throws IOException {
		String name = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
		String path = request.getSession().getServletContext().getRealPath(temporary_dir);
		File file = new File(path);
		if(!file.exists() && !file.isDirectory()){
			file.mkdir();
		}
		String filepath = path+"/"+name;
		boolean tag = UploadUtil.generateImage(imgstr, path+"/"+name);
		if(tag){
			String key = UploadUtil.upload(filepath);
			if(!StringUtil.isEmpty(key)){
				UploadUtil.deleteFiles(filepath);
				String imgurl = pichost+key;
				return imgurl;
			}
		}
		return null;
	}
}
