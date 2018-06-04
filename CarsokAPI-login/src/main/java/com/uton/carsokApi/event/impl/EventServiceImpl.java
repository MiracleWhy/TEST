package com.uton.carsokApi.event.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.EventHistoryMapper;
import com.uton.carsokApi.dao.EventMapper;
import com.uton.carsokApi.event.EventService;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventStatusEnum;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.EventHistory;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventMapper eventMapper;
	@Autowired
	private EventHistoryMapper eventHistoryMapper;
	@Override
	public BaseResult saveEvent(BaseEvent event) {
		event.setRetryTime(0);
		event.setGmtCreate(new Date());
		event.setEventStatus(EventStatusEnum.WAIT_HANDLE.name());
		event.setGmtModify(new Date());
		eventMapper.insertSelective(event);
		return BaseResult.success();
	}

	@Override
	public List<BaseEvent> searchHandleEvent(String status) {
		// TODO Auto-generated method stub
		return eventMapper.selectEventByStatus(status);
	}

	@Override
	public BaseEvent findOneForUpdate(Integer eventProcessId) {
		// TODO Auto-generated method stub
		return eventMapper.selectByPrimaryKey(eventProcessId);
	}

	@Override
	public void handleCallBack(BaseEvent event, OperateResult result) {
		if (event == null) {
			return;
		}
		event.setGmtModify(new Date());
		// 调用正确回调
		if (result.isSuccess() && StringUtils.isEmpty(result.getMessage())) {
			event.setException(result.getMessage());
			event.setEventStatus(EventStatusEnum.HANDLE_SUCCESS.name());
			eventMapper.updateByPrimaryKeySelective(event);
		}
	 else if (!result.isSuccess() && event.getRetryTime() < EventConstants.MAX_RETRY_TIME) {
			event.setRetryTime(event.getRetryTime()+1);
			event.setException(result.getMessage());
			event.setEventStatus(EventStatusEnum.WAIT_RETRY.name());
			eventMapper.updateByPrimaryKeySelective(event);
		} else {
			event.setException(result.getMessage());
			event.setEventStatus(EventStatusEnum.HANDLE_FAILED.name());
			eventMapper.updateByPrimaryKeySelective(event);
		}
	}

	@Override
	public List<BaseEvent> searchWaitDeleteEvent() {
		return eventMapper.selectWaitDeleteEvent();
	}

	@Override
	public void removeEventToHistory(BaseEvent event) {
		eventMapper.deleteByPrimaryKey(event.getId());
		eventHistoryMapper.insertByBaseEvent(event);
	}

}
