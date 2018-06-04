package com.uton.carsokApi.service.core.task;

import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.service.ICarsokTenureTaskService;
import com.uton.carsokApi.service.core.SpringContextTool;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by WANGYJ on 2017/11/13.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class DelayCheckJob implements Job {
    Logger logger = Logger.getLogger(DelayCheckJob.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("执行任务处理开始");
        ICarsokTenureTaskService carsokTenureTaskService = (ICarsokTenureTaskService)SpringContextTool.getApplicationContext().getBean("taskservice");
        List<CarsokTenureTask> list = carsokTenureTaskService.queryAllReadyTasks();
        Date  curdate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfdetail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            curdate = sdfdetail.parse(sdf.format(new Date())+" 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (CarsokTenureTask carsokTenureTask:list){
            if(carsokTenureTask.getScheduledTime() != null){
                if (carsokTenureTask.getScheduledTime().before(curdate)){
                    carsokTenureTask.setTaskStatus(TaskStatusEnums.delay.name());
                    Boolean result = carsokTenureTaskService.updateById(carsokTenureTask);
                    if (!result){
                        JSONObject jsonObject = JSONObject.fromObject(carsokTenureTask);
                        logger.error("更新状态失败："+jsonObject.toString());
                    }
                }
            }
        }
        logger.error("执行任务结束。");
    }
}
