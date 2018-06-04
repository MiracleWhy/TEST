package com.uton.carsokApi.service.core.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uton.carsokApi.model.CarsokAccountTofront;
import com.uton.carsokApi.model.CarsokCarTofront;
import com.uton.carsokApi.model.CarsokProduct;
import com.uton.carsokApi.service.ICarsokProductService;
import com.uton.carsokApi.service.core.SpringContextTool;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.util.List;
import java.util.Random;

/**
 * Created by zhangdi on 2018/1/26.
 * desc:
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class UpdateNewOrderJob implements Job {

    Logger logger = Logger.getLogger(ClearDayViewJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("执行任务处理开始");
        List<CarsokCarTofront> carsokCarTofront =new CarsokCarTofront().selectList(new EntityWrapper().eq("enable",0));
        if (carsokCarTofront.size() > 0) {
            for (CarsokCarTofront tofront : carsokCarTofront) {
                tofront.setNewOrder(new Random().nextInt(10000));
                tofront.updateById();
            }
        }
        List<CarsokAccountTofront> carsokAccountTofronts = new CarsokAccountTofront().selectList(new EntityWrapper().eq("enable",0));
        if (carsokAccountTofronts.size() >0) {
            for (CarsokAccountTofront carsokAccountTofront : carsokAccountTofronts) {
                carsokAccountTofront.setNewOrder(new Random().nextInt(10000));
                carsokAccountTofront.updateById();
            }
        }
        logger.error("执行任务结束。");
    }
}