package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.EventHistory;

public interface EventHistoryMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(EventHistory record);

	int insertSelective(EventHistory record);

	EventHistory selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(EventHistory record);

	int updateByPrimaryKeyWithBLOBs(EventHistory record);

	int updateByPrimaryKey(EventHistory record);

	void insertByBaseEvent(BaseEvent event);
}