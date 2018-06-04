package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;

import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2017/11/11.
 */
public interface FollowUpMsgService {
    void insertNewMsg(FollowUpMsgRequest u);
    int insertTaskTable(FollowUpMsgRequest u);
    void updateTaskTable(FollowUpMsgRequest u);
    List<ChildAccount> selectReallocateList(String account_phone);
    void insertCarsokRecord(FollowUpMsgRequest u,String change);
    int followUpDistribution(Acount acount, FollowUpDistributionRequest u);
    void insertCustomerFlowMsg(FollowUpDistributionRequest u);
//    void insertTaskTableDistribution(FollowUpMsgRequest u);
    List<String> selectTaskid(String custId);
    void posCusC(PosCusRequest u);
    void testDrive(TestDriveRequest u);
    List<CarsokChildAccount> selectPowerByChildId(List<CarsokChildAccount> carsokChildAccounts);
}
