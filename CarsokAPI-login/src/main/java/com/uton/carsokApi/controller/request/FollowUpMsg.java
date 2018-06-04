package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by SEELE on 2017/11/17.
 */
@Data
public class FollowUpMsg {

    private String custId;

    //    前台传
    private String taskId;

    //    标志位（是销售顾问0还是经理1）
    private String flag;

    private String customerFlowMessage;

    private String level;

    private String defeat;

    private String cusServiceFollow;

    private String  create_time;

    private String update_time;

    private String account_id;

    private String child_id;
}
