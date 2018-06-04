package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by Administrator on 2017/11/22.
 */
@Data
public class RePurchaseFollowUpMsgRequest {
    //customerId
    private String custId;
    //标志位（是销售顾问0还是经理1）
    private String flag;
    //跟进信息
    private String customerFlowMessage;
    //客户级别
    private String level;

}
