package com.uton.carsokApi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.BannerMapper;
import com.uton.carsokApi.model.Banner;

@Service
public class BannerService {
	private final static Logger logger = Logger.getLogger(BannerService.class);
	
	@Autowired
	BannerMapper bannerMapper;
	
	public BaseResult getBanners() {
		List<Banner> list = bannerMapper.searchBanners();
		return BaseResult.success(list);
	}
}
