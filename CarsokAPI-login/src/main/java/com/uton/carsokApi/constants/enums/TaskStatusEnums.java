package com.uton.carsokApi.constants.enums;

/**
 * Created by WANGYJ on 2017/11/11.
 */
public enum TaskStatusEnums {
    ready("就绪"),
    delay("延迟"),
    finish("完成"),
    terminate("终止"),
    ;
    String taskstatus;

    TaskStatusEnums(String taskstatus) {
        this.taskstatus = taskstatus;
    }
    String getTaskstatus(){
        return taskstatus;
    }
}
