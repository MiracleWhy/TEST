package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CreateCarServeInfoRequest;
import com.uton.carsokApi.controller.request.FindDetailInfoRequest;
import com.uton.carsokApi.controller.request.FindListRequest;
import com.uton.carsokApi.controller.request.SaveCarAndCustomeInfoRequest;
import com.uton.carsokApi.model.*;

import java.util.List;

public interface SaasTenureCustomerService {

    BaseResult findList(FindListRequest vo, Acount acount, int childId);

    List<String> getRoleName(int childId);

    BaseResult insertOrUpdateInfo(SaveCarAndCustomeInfoRequest vo, int type);

    BaseResult findDetailInfo(FindDetailInfoRequest vo);

    void runTenureCarTask(CarsokCustomerTenureCar tenureCar, CarsokCustomerTenureCar oldCar);

    void runCustomerTask(CarsokCustomer customer, CarsokCustomer oldCustomer);


    /**
     * 单独跑某一保有车和保有客户的任务
     * @param tenureCar   保有车信息
     * @param oldCar      若是修改, 则传保有车旧信息, 非修改传null或者new CarsokCustomerTenureCar()
     * @param customer    保有客户信息
     * @param oldCustomer 若是修改, 则传保有客户旧信息, 非修改传null或者new CarsokCustomer()
     */
    void runSingleTask(CarsokCustomerTenureCar tenureCar, CarsokCustomerTenureCar oldCar, CarsokCustomer customer, CarsokCustomer oldCustomer);

//    BaseResult moveOldVersionData();
//    BaseResult moveOldVersionQiankeData();
//    BaseResult dealDecBaoyouCustomerTenureData();
//    BaseResult dealNovBaoyouCustomerTenureData();

    BaseResult pushOutDateTaskRemind(Acount acount, ChildAccount childAccount);

    BaseResult OneKeyRemindShow(Acount acount, ChildAccount childAccount, String timeParam);

    List<ChildAccount> getChildAcountList();

    List<Acount> getAcountList();

    int clearReadyTaskPush();

    BaseResult canMessageShow(Acount acount, ChildAccount childAccount);

    BaseResult getCarServeInfo(CreateCarServeInfoRequest vo);

    BaseResult createCarServe(CarsokCustomerTenureServe vo);

//    void clearOldTask();
}
