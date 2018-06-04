package com.uton.carsokApi.controller.request;

import com.uton.carsokApi.model.ChildAccount;
import lombok.Data;

import java.util.List;

/**
 * Created by SEELE on 2017/11/14.
 */
@Data
public class FollowUpReallocateRequest {

    private String custId;

    private String customerFlowMessage;

    private String level;

//    待分配列表
    private List<ChildAccount> Reallocate;
}
