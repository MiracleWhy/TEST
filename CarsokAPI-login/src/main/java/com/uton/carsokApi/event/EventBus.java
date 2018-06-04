package com.uton.carsokApi.event;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventException;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventStatusEnum;
import com.uton.carsokApi.event.helper.EventHandleHelper;
import com.uton.carsokApi.event.task.EventTaskExecutor;
import com.uton.carsokApi.model.BaseEvent;

@Service
public class EventBus {
	private static Logger logger = LoggerFactory.getLogger(EventBus.class);

	@Autowired
	protected EventTaskExecutor eventTaskExecutor;

	@Autowired
	protected EventService eventService;
	
	@Autowired
	private EventHandleHelper eventHandleHelper;

	public void searchAndHandleWaitHandleEvent() {
		List<BaseEvent> events = eventService.searchHandleEvent(EventStatusEnum.WAIT_HANDLE.name());
		// logger.info("待处理事件数量: " + events.size());
		final CountDownLatch latch = new CountDownLatch(events.size());
		for (final BaseEvent event : events) {
			eventTaskExecutor.getThreadPool(EventConstants.WAIT_HANDLER_EVENT_TASK, 20).execute(new Runnable() {
				@Override
				public void run() {
					try {
						// handleEventProcess方法内报异常只回滚内部事务
						handleEventProcess(event);
					} catch (EventException e) {
						logger.error(e.getMessage());
					} catch (Exception e) {
						logger.error(String.format("处理事件的时候发生异常, EventProcess[id=%d]", event.getId()), e);
					} finally {
						latch.countDown();
					}
				}

			});
		}
		try {
			// 等待事件异步处理完成
			latch.await();
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	public void handleEventProcess(BaseEvent event) {
		logger.info(String.format("handle event process, id: %d, event category: %s ", event.getId(),
				event.getEventName()));
		boolean success = eventHandleHelper.setEventKey(event);
		if (success == true) {
			eventHandleHelper.handle(event, eventService);
		} else {
			logger.info(String.format("处理事件,获取锁失败 id: %d,name : %s ", event.getId(), event.getEventName()));
			return;
		}

	}

	public void searchAndHandleWaitRetryEvent() {
		List<BaseEvent> events = eventService.searchHandleEvent(EventStatusEnum.WAIT_RETRY.name());
		// logger.info("待处理事件数量: " + events.size());
		final CountDownLatch latch = new CountDownLatch(events.size());
		for (final BaseEvent event : events) {
			eventTaskExecutor.getThreadPool(EventConstants.WAIT_RETRY_EVENT_TASK, 10).execute(new Runnable() {
				@Override
				public void run() {
					try {
						handleEventProcess(event);
					} catch (EventException e) {
						logger.error(e.getMessage());
					} catch (Exception e) {
						logger.error(String.format("处理事件的时候发生异常, EventProcess[id=%d]", event.getId()), e);
					} finally {
						latch.countDown();
					}
				}

			});
		}
		try {
			// 等待事件异步处理完成
			latch.await();
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	/**
	 * 发送事件<br>
	 * TODO 暂时一个事件只允许有一个处理器，以后考虑多处理器. <br>
	 * 暂时没有考虑data数据较大时，会占用大量内存.<br>
	 * 
	 * @param event
	 * @return
	 */
	public BaseResult publish(BaseEvent event) {
		return eventService.saveEvent(event);
	}

	public void serchAndHandleHistoryEvent() {
		List<BaseEvent> events = eventService.searchWaitDeleteEvent();
		final CountDownLatch latch = new CountDownLatch(events.size());
		for (final BaseEvent event : events) {
			eventTaskExecutor.getThreadPool(EventConstants.DEFAULT_TASK, 10).execute(new Runnable() {
				@Override
				public void run() {
					try {
						handleDeleteEventProcess(event);
					} catch (EventException e) {
						logger.error(e.getMessage());
					} catch (Exception e) {
						logger.error(String.format("处理事件的时候发生异常, EventProcess[id=%d]", event.getId()), e);
					} finally {
						latch.countDown();
					}
				}

			});
		}
		try {
			// 等待事件异步处理完成
			latch.await();
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	protected void handleDeleteEventProcess(BaseEvent event) {
		boolean success = eventHandleHelper.setEventKey(event);
		if (success == true) {
			eventService.removeEventToHistory(event);
		} else {
			logger.info(String.format("处理事件,获取锁失败 id: %d,name : %s ", event.getId(), event.getEventName()));
			return;
		}
	}

}
