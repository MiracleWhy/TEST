package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.request.FollowUpDistributionRequest;
import com.uton.carsokApi.controller.request.FollowUpMsg;
import com.uton.carsokApi.controller.request.FollowUpMsgRequest;
import com.uton.carsokApi.model.ChildAccount;

import java.util.List;

/**
 * Created by SEELE on 2017/11/11.
 */
public interface FollowUpMsgMapper {
    int insertMsg(FollowUpMsg f);
    int updateMsg(FollowUpMsg f);
//    int insertMsg();
    int updateDistribution(FollowUpDistributionRequest u);
    List<ChildAccount> selectReallocate(String account_phone);
    List<String> selectTaskIdByCusId (String custId);
}
