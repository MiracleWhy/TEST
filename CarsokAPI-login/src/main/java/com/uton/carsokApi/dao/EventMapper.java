package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.BaseEvent;

public interface EventMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(BaseEvent record);

	int insertSelective(BaseEvent record);

	BaseEvent selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BaseEvent record);

	int updateByPrimaryKeyWithBLOBs(BaseEvent record);

	int updateByPrimaryKey(BaseEvent record);

	List<BaseEvent> selectEventByStatus(String status);

	List<BaseEvent> selectWaitDeleteEvent();
}