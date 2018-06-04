package com.uton.carsokApi.listener;

import java.io.File;
import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;

import com.uton.carsokApi.util.FastdfsFastddhclient;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		try {
	        //初始化FastDFS 加载配置文件
	        String dfsPath = servletContext.getRealPath("/WEB-INF/classes");
	        String fdfsConfigFilename = dfsPath + File.separatorChar + "fdfs_client.conf";
	        String fdhtConfigFilename = dfsPath + File.separatorChar + "fdht_client.conf";
	        FastdfsFastddhclient.init(fdfsConfigFilename, fdhtConfigFilename);
	        
       } catch (Exception e) {
    	   e.printStackTrace();
       }
		
		return super.initWebApplicationContext(servletContext);
	}
}
