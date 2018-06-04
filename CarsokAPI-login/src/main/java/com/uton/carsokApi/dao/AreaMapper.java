package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.Area;

public interface AreaMapper {
	
	List<Area> searchAreaBypid(int pid);

	List<Area> findCity(Integer id);
}

