package com.uton.carsokApi.dao;

import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.FindListResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokCustomer;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.PushContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaasTenureCustomerMapper {

    List<String> getRoleName(@Param("childId")int childId);

    Page<FindListResponse> findCarList(@Param("roleType")int roleType, @Param("accountId")int accountId, @Param("childId")int childId, @Param("type")int type, @Param("selects")String selects, @Param("completeStatus")int completeStatus, @Param("times")int times, @Param("buyStatusList")List<String> buyStatusList);

    Page<FindListResponse> findTaskList(@Param("roleType")int roleType, @Param("accountId")int accountId, @Param("childId")int childId, @Param("type")int type);

    FindListResponse selectOtherInfoByCarId(@Param("carId")String carId);

    FindListResponse selectOtherInfoByCustomerId(@Param("customerId")String customerId);

//    List<moveOldDataRequest> getOldData();
//
//    List<moveOldDataCarsokCustomerManage> getOldQiankeData();
//
//    List<DecBaoyouDataComplete> getDecOldBaoyouCustomerData();
//
//    CarsokCustomer selectphoneC(CarsokCustomer phoneC);
//
//    int updateCarsokCustomerById(CarsokCustomer carsokCustomer);
//
//    List<NovBaoyouData> getNovOldData();

    List<PushContent> getOutDatePushList(@Param("pushTo") String pushTo, @Param("contentType") String contentType, @Param("timeParam")String timeParam);

    List<ChildAccount> getChildAcountList();

    List<Acount> getAcountList();

    int clearReadyTaskPush();

//    void updateTaskLevel(@Param("taskId") int taskId, @Param("level")String level);
//
//    void clearTaskScheduledTime(@Param("taskId")int taskId);
}