package com.uton.carsokApi.service.core.task;

import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.MessageCenter;
import com.uton.carsokApi.service.ITaskFacade;
import com.uton.carsokApi.service.MessageCenterService;
import com.uton.carsokApi.service.PushService;
import com.uton.carsokApi.service.SaasTenureCustomerService;
import com.uton.carsokApi.service.core.SpringContextTool;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANGYUGONG on 2017/12/21.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ReadyTaskPushJob implements Job {
    Logger logger = Logger.getLogger(ReadyTaskPushJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("执行任务处理开始");
        SaasTenureCustomerService saasTenureCustomerService = (SaasTenureCustomerService) SpringContextTool.getApplicationContext().getBean("runtaskservice");
        ITaskFacade iTaskFacade = (ITaskFacade) SpringContextTool.getApplicationContext().getBean("itaskfacade");
        PushService pushService = (PushService) SpringContextTool.getApplicationContext().getBean("pushservice");
        MessageCenterService messageCenterService = (MessageCenterService) SpringContextTool.getApplicationContext().getBean("messagecenterservice");
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        //先删除所有之前的待办推送
        saasTenureCustomerService.clearReadyTaskPush();
        //查询每个人(主账号和子账号)的潜客和保有待办
//        //主账号的, 先查所有主账号
//        List<Acount> acountList = saasTenureCustomerService.getAcountList();
//        //循环, 查出每个主账号是否有关联的待办任务
//        for (Acount acount : acountList){
//            FilterSQLParam sqlParam = new FilterSQLParam();
//            String sql = " task_status='"+ TaskStatusEnums.ready +"' AND DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') " +
//                    " AND ((json_extract(extra_fields,'$.accountId')="+ acount.getId() +" AND json_extract(extra_fields,'$.childId')=0) OR (json_extract(extra_fields,'$.account_id')='"+ acount.getId() +"' AND json_extract(extra_fields,'$.child_id')='0'))";
//            sqlParam.setSqlTemplate(sql);
//            PageInfo<CarsokTenureTask> taskInfo = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam);
//            //如果有待办任务, 则推送给该账号
//            if(taskInfo.getList().size() > 0){
//                String content = today + " 待办";
//                boolean pushResult = pushService.SendCustomizedCast(acount.getAccount(), content,"readytask");
//                MessageCenter mc = new MessageCenter();
//                mc.setTitle("待办通知");
//                mc.setContent(content);
//                mc.setCreateTime(new Date());
//                mc.setEnable(1);
//                mc.setPushTo(acount.getAccount());
//                mc.setPushFrom("systems");
//                mc.setContentType("taskReady");
//                mc.setPushStatus(2);
//                messageCenterService.messageCenterAdd(mc);
//                if(pushResult){
//                    messageCenterService.updatePushStatusById(mc.getId(),1);
//                }else {
//                    messageCenterService.updatePushStatusById(mc.getId(),0);
//                }
//            }
//        }
        //子账号的, 先查所有子账号
        List<ChildAccount> childList = saasTenureCustomerService.getChildAcountList();
        //循环, 查出每个主账号是否有关联的待办任务
        for (ChildAccount childAccount : childList){
            FilterSQLParam sqlParam = new FilterSQLParam();
            String sql = " task_status='"+ TaskStatusEnums.ready +"' AND DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') " +
                    " AND (json_extract(extra_fields,'$.childId')="+ childAccount.getId() +" OR json_extract(extra_fields,'$.child_id')='"+ childAccount.getId() +"') ";
            sqlParam.setSqlTemplate(sql);
            PageInfo<CarsokTenureTask> taskInfo = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam);
            //如果有待办任务, 则推送给该账号
            if(taskInfo.getList().size() > 0){
                String content = today + " 待办";
                boolean pushResult = pushService.SendCustomizedCast(childAccount.getChildAccountMobile(), content,"readytask");
                MessageCenter mc = new MessageCenter();
                mc.setTitle("待办通知");
                mc.setContent(content);
                mc.setCreateTime(new Date());
                mc.setEnable(1);
                mc.setPushTo(childAccount.getChildAccountMobile());
                mc.setPushFrom("systems");
                mc.setContentType("taskReady");
                mc.setPushStatus(2);
                messageCenterService.messageCenterAdd(mc);
                if(pushResult){
                    messageCenterService.updatePushStatusById(mc.getId(),1);
                }else {
                    messageCenterService.updatePushStatusById(mc.getId(),0);
                }
            }
        }
        logger.error("执行任务结束");
    }
}
