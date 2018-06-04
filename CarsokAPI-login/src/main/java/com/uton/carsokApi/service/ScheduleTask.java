package com.uton.carsokApi.service;

import com.uton.carsokApi.dao.PruductOldcarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 车辆管理 定时任务
 */
@Component
@Transactional
public class ScheduleTask {

	@Autowired
	private PruductOldcarMapper pruductOldcarMapper;

	@Value("${autoRandom.browseNumTimes}")
	private String autoRandom_browseNumTimes;

	@Scheduled(cron = "1 0 0 1/1 * ?")
	public void excute(){
		if("yes".equals(autoRandom_browseNumTimes)){
			pruductOldcarMapper.updateVehicleSupervise();
		}
	}

}