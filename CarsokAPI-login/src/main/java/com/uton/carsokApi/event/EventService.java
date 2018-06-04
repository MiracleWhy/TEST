package com.uton.carsokApi.event;

import java.util.List;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;

public interface EventService {
	public BaseResult saveEvent(BaseEvent event);

	public List<BaseEvent> searchHandleEvent(String status);

	public BaseEvent findOneForUpdate(Integer eventProcessId);

	public void handleCallBack(BaseEvent event, OperateResult success);
	
	public List<BaseEvent> searchWaitDeleteEvent();

	public void removeEventToHistory(BaseEvent event);
}
