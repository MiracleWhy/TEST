package com.uton.carsokApi.event.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.event.EventBus;

@Service
public class EventHandleTask {
	@Autowired
	private EventBus eventBus;

	@Scheduled(fixedRate = 500L)
	public void handleWaitHandleEvent() {
		eventBus.searchAndHandleWaitHandleEvent();
	}

	@Scheduled(fixedRate = 2000L)
	public void handleWaitRetryEvent() {
		eventBus.searchAndHandleWaitRetryEvent();
	}
	@Scheduled(fixedRate=60*1000L)
	public void handleHistoryEvent(){
		eventBus.serchAndHandleHistoryEvent();
	}
}
