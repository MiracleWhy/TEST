package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/11/11.
 */

@Data
public class FollowUpMsgRequest implements Serializable{

    private String custId;

//    前台传
    private String taskId;

//    标志位（是销售顾问0还是经理1）
    private String flag;

    private String customerFlowMessage;

    private String level;

    private String defeat;

    private String cusServiceFollow;

    private String account_id;

    private String child_id;

}
