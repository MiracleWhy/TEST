package com.uton.carsokApi.controller.request;

import lombok.Data;
import org.dozer.Mapping;

import java.util.Date;

@Data
public class SaveCarAndCustomeInfoRequest {
    /**
     * 保有车辆id
     */
    private int id;
    /**
     * 保有车车型
     */
    private String tenureCarname;
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
    private String tenureCarprice;
    /**
     * 行驶里程
     */
    private String carMiles;
    /**
     * 是否试驾
     */
    private String isDrivingTest;
    /**
     * 车牌号
     */
    private String tenureCarnum;
    /**
     * 付款方式
     */
    private String tenureCartype;
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
//    /**
//     * 年检到期日
//     */
//    private String tenureInspection;
//    /**
//     * 救援日期
//     */
//    private String tenureRescue;
//    /**
//     * 洗车日期
//     */
//    private String tenureCarwash;
//    /**
//     * 钣金喷漆日期
//     */
//    private String tenurePaint;
    /**
     * 购买状态
     */
    private String purchaseStatus;
    /**
     * 保有客户id
     */
    private int customerId;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 客户姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 来源
     */
    private String source;
    /**
     * 年龄
     */
    private String age;
    /**
     * 住址
     */
    private String address;
    /**
     * 职业
     */
    private String job;
    /**
     * 性格
     */
    private String personality;
    /**
     * 人脉
     */
    private String link;
    /**
     * 购车之前车型
     */
    private String beforeCar;
    /**
     * 备注
     */
    private String remark;
    /**
     * accountId
     */
    private Integer accountId;
    /**
     * childId
     */
    private Integer childId;
    /**
     *生日
     */
    private Date birthday;
}
