package com.uton.carsokApi.service.core.task;

import com.uton.carsokApi.service.ICarsokProductOldcarService;
import com.uton.carsokApi.service.ICarsokProductService;
import com.uton.carsokApi.service.core.SpringContextTool;
import org.apache.log4j.Logger;
import org.quartz.*;

/**
 * Created by cuisw on 2018/1/10.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ClearDayViewJob implements Job{

    Logger logger = Logger.getLogger(ClearDayViewJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("执行任务处理开始");
        ICarsokProductService service = (ICarsokProductService) SpringContextTool.getApplicationContext().getBean("carsokproductservice");
        Boolean result = service.clearDayViewCount();
        if(!result){
            logger.error("清空当日浏览量失败！");
        }
        logger.error("执行任务结束。");
    }
}
