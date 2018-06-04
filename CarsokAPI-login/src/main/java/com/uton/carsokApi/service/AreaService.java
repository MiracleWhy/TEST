package com.uton.carsokApi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.AreaMapper;
import com.uton.carsokApi.model.Area;

@Service
public class AreaService {
	
	@Autowired
	AreaMapper areaMapper;
	
	public BaseResult searchArea(int pid){
		List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
		List<Area> area_list = areaMapper.searchAreaBypid(pid);
		if(area_list!=null && area_list.size()>0){
			for(Area area:area_list){
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("id", area.getId());
				item.put("name", area.getName());
				items.add(item);
			}
		}
		return BaseResult.success(items);
	}
	
	public BaseResult findCity(Integer id) {
		List<Area> areas = areaMapper.findCity(id);
		return BaseResult.success(areas);
	}
}
