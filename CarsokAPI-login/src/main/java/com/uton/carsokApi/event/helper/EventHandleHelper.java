package com.uton.carsokApi.event.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.event.EventService;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;

@Service
public class EventHandleHelper implements BeanPostProcessor {
	@Autowired
	private RedisTemplate redisTemplate;

	private Map<String, EventHandle> eventHandleMap = new HashMap<String, EventHandle>();

	public Logger logger = LoggerFactory.getLogger(EventHandleHelper.class);


	public EventHandle getHandle(String name) {
		return eventHandleMap.get(name);
	}

	public void handle(BaseEvent event, EventService eventService) {
		EventHandle eventHandle = getHandle(event.getEventName());
		logger.info("处理事件 event:" + JSONObject.toJSONString(event) + ",获取处理器handle:"
				+ JSONObject.toJSONString(eventHandle));
		// 如果处理器不为空，则处理事件
		OperateResult result = new OperateResult();
		try {
			if (eventHandle != null) {
				result = eventHandle.handle(event);
			} else {
				result = new OperateResult(true, EventConstants.NO_HANDLER_ERROR);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		} finally {
			logger.info("事件处理结果eventId:" + event.getId() + ",result:" + JSONObject.toJSONString(result));
			// 是否成功进行回调 修改事件状态
			eventService.handleCallBack(event, result);
			deleteEventKey(event);
		}

	}

	public boolean deleteEventKey(BaseEvent event) {
		String key = EventConstants.prefix + "_" + event.getEventName() + "_" + event.getId();
		redisTemplate.delete(key);
		return true;
	}

	public boolean setEventKey(BaseEvent event) {
		String key = EventConstants.prefix + "_" + event.getEventName() + "_" + event.getId().toString();
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String eventKey = valueOperations.get(key);
		if (StringUtils.isEmpty(eventKey)) {
			redisTemplate.opsForValue().set(key, event.getId().toString(), 30, TimeUnit.SECONDS);
			return true;
		}
		return false;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		EventHandler handler = bean.getClass().getAnnotation(EventHandler.class);
		if (handler != null) {
			EventHandle handle = (EventHandle) bean;
			handle.setName(handler.name());
			eventHandleMap.put(handler.name(), handle);
		}
		return bean;
	}

}