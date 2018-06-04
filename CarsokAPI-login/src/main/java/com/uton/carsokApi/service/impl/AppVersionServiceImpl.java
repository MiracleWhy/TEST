package com.uton.carsokApi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.AppVersionMapper;
import com.uton.carsokApi.model.AppVersion;
import com.uton.carsokApi.pagination.PaginationInfo;
import com.uton.carsokApi.service.AppVersionService;
import com.uton.carsokApi.util.StringUtil;

@Service
public class AppVersionServiceImpl implements AppVersionService {

	private Logger logger = Logger.getLogger(AppVersionServiceImpl.class);

	@Autowired
	private AppVersionMapper appVersionMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return appVersionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AppVersion record) {
		// TODO Auto-generated method stub
		return appVersionMapper.insert(record);
	}

	@Override
	public int insertSelective(AppVersion record) {
		// TODO Auto-generated method stub
		return appVersionMapper.insertSelective(record);
	}

	@Override
	public AppVersion selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return appVersionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AppVersion record) {
		// TODO Auto-generated method stub
		return appVersionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AppVersion record) {
		// TODO Auto-generated method stub
		return appVersionMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<AppVersion> selectByPage(AppVersion record) {
		// TODO Auto-generated method stub
		return appVersionMapper.selectByPage(record);
	}

}
