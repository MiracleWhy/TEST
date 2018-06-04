package com.uton.carsokApi.event.constants;

import org.jvnet.hk2.annotations.Service;

import com.uton.carsokApi.event.handler.Handle;
import com.uton.carsokApi.model.BaseEvent;
@Service
public abstract class EventHandle implements Handle<BaseEvent> {

	private int maxRetry;
	
	private String name;

	public int getMaxRetry() {
		return maxRetry;
	}

	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
