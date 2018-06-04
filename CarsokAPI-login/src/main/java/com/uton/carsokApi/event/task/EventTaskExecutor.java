package com.uton.carsokApi.event.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.event.constants.EventConstants;

@Service
public class EventTaskExecutor extends ThreadPoolTaskExecutor {
	
	 private Map<String,ThreadPoolTaskExecutor> taskMap=new HashMap();
	 
	 public ThreadPoolTaskExecutor getThreadPool(String name,int poolSize){
		 if(StringUtils.isEmpty(name)){
			 return taskMap.get(EventConstants.DEFAULT_TASK);
		 }
		 ThreadPoolTaskExecutor threadPoolTaskExecutor= taskMap.get(name);
		 if(threadPoolTaskExecutor==null){
			 synchronized (taskMap) {
				 threadPoolTaskExecutor= taskMap.get(name);
				 if(threadPoolTaskExecutor==null){
					 threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
					 threadPoolTaskExecutor.setCorePoolSize(poolSize);
					 threadPoolTaskExecutor.setMaxPoolSize(poolSize);
					 threadPoolTaskExecutor.setQueueCapacity(10000);
					 threadPoolTaskExecutor.setThreadNamePrefix(name+"-");
					 threadPoolTaskExecutor.initialize();
					 taskMap.put(name,threadPoolTaskExecutor);
				 }
			}
		 }
		 return threadPoolTaskExecutor;
	 }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EventTaskExecutor() {
		setCorePoolSize(20);
		setMaxPoolSize(20);
		setQueueCapacity(10000);
		setThreadNamePrefix(EventConstants.DEFAULT_TASK);
		taskMap.put(EventConstants.DEFAULT_TASK, this);
		ThreadPoolTaskExecutor handler=new ThreadPoolTaskExecutor();
		handler.setCorePoolSize(20);
		handler.setMaxPoolSize(20);
		handler.setQueueCapacity(10000);
		handler.setThreadNamePrefix(EventConstants.WAIT_HANDLER_EVENT_TASK);
		handler.initialize();
		taskMap.put(EventConstants.WAIT_HANDLER_EVENT_TASK, handler);
		ThreadPoolTaskExecutor retry=new ThreadPoolTaskExecutor();
		retry.setCorePoolSize(10);
		retry.setMaxPoolSize(20);
		retry.setQueueCapacity(10000);
		retry.setThreadNamePrefix(EventConstants.WAIT_RETRY_EVENT_TASK);
		retry.initialize();
		taskMap.put(EventConstants.WAIT_RETRY_EVENT_TASK, retry);
	}
}
