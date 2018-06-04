package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by SEELE on 2017/11/14.
 */
@Data
public class FollowUpDistributionRequest {

    private String taskId;

    private String custId;

    private String customerFlowMessage;

    private String level;

    private String account_id;

    private String child_id;

    private String id;
}
