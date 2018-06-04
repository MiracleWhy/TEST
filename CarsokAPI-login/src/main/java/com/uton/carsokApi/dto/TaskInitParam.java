package com.uton.carsokApi.dto;

import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.constants.enums.TaskTypeEnums;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Created by WANGYJ on 2017/11/10.
 */
@Data
public class TaskInitParam {
    //任务类型
    private TaskTypeEnums type;
    //关联ID
    private String business_id;
    //任务执行日期
    private Date scheduled_time;
    //任务执行的关联组别
    private String task_execute_group;
    //任务的关联Extra信息，Map存储
    private Map<String,String> extraFields;
    //模块Id
    private ModuleEnums module;
    //任务状态
    private TaskStatusEnums statusEnums;
}
