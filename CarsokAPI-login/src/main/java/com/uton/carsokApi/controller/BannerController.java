package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.service.BannerService;

@Controller
public class BannerController {
	private final static Logger logger = Logger.getLogger(BannerController.class);
	
	@Autowired
	BannerService bannerService;
	
	/**
	 * 获取banner列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/banners" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult banners(HttpServletRequest request) {
		try {
			return bannerService.getBanners();
		} catch (Exception e) {
			logger.error("banners error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
}
