package com.uton.carsokApi.service.core.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.model.CarsokCustomer;
import com.uton.carsokApi.model.CarsokCustomerTenureCar;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.service.ICarsokTenureTaskService;
import com.uton.carsokApi.service.SaasTenureCustomerService;
import com.uton.carsokApi.service.core.SpringContextTool;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WANGYJ on 2017/11/13.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class RunTaskJob implements Job {
    Logger logger = Logger.getLogger(RunTaskJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SaasTenureCustomerService saasTenureCustomerService = (SaasTenureCustomerService)SpringContextTool.getApplicationContext().getBean("runtaskservice");
        //所有保有车
        List<CarsokCustomerTenureCar> carList = new CarsokCustomerTenureCar().selectList(new EntityWrapper().orderBy("sale_time", true));
        //保有车相关所有保有客户列表(去重)
        List<CarsokCustomer> customerList = new ArrayList<>();
        List<CarsokCustomerTenureCar> list = new CarsokCustomerTenureCar().selectList(new EntityWrapper().groupBy("customer_id").orderBy("customer_id", true));
        for (CarsokCustomerTenureCar car : list){
            CarsokCustomer customer = new CarsokCustomer().selectOne(new EntityWrapper().eq("id", car.getCustomerId()));
            if (customer != null){
                customerList.add(customer);
            }
        }
        //循环保有车, 跑任务
        for (CarsokCustomerTenureCar car : carList){
            saasTenureCustomerService.runTenureCarTask(car, null);
        }
        //循环保有客户, 跑任务
        for (CarsokCustomer customer : customerList){
            saasTenureCustomerService.runCustomerTask(customer, null);
        }
    }
}
