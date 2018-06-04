package com.uton.carsokApi.controller.request;

import lombok.Data;
@Data
public class SaveCarInfoRequest {
    /**
     * 保有车辆id
     */
    private int tenureCarId;
    /**
     * 保有车车型
     */
    private String tenurearName;
    /**
     * 车架号vin
     */
    private String tenureVin;
    /**
     * 出售时间
     */
    private String saleTime;
    /**
     * 出售价格
     */
    private String tenureCarPrice;
    /**
     * 行驶里程
     */
    private String carMales;
    /**
     * 是否试驾
     */
    private String isDrivingTest;
    /**
     * 车牌号
     */
    private String tenureCarNum;
    /**
     * 付款方式
     */
    private String tenureCarType;
    /**
     * 交强险到期
     */
    private String tenureCompulsory;
    /**
     * 是否投商业保险
     */
    private String isBussiness;
    /**
     * 商业险到期
     */
    private String tenureBusiness;
    /**
     * 保险到期
     */
    private String tenureMaintain;
    /**
     * 质保到期
     */
    private String tenureWarranty;
    /**
     * 购买状态
     */
    private String purchaseStatus;
}
