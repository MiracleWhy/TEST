package com.uton.carsokApi.controller.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class FindDetailInfoResponse implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Integer tenureCarId;

    /**
     * 车辆名称
     */
    private String tenureCarname;

    /**
     * 车辆vin码
     */
    private String tenureVin;

    /**
     * 车牌号
     */
    private String tenureCarNum;

    /**
     * 出售方式
     */
    private String tenureCarType;

    /**
     * 交强险到期日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date tenureCompulsory;

    /**
     * 商业险到期日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date tenureBusiness;

    /**
     * 保养到期日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date tenureMaintain;

    /**
     * 质保到期日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date tenureWarranty;

//    /**
//     * 年检到期日
//     */
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//    private Date tenureInspection;
//
//    /**
//     * 救援日期
//     */
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//    private Date tenureRescue;
//
//    /**
//     * 洗车日期
//     */
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//    private Date tenureCarwash;
//
//    /**
//     * 钣金喷漆日期
//     */
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//    private Date tenurePaint;

    /**
     * 价格
     */
    private BigDecimal tenureCarPrice;

    /**
     * 行驶里程
     */
    private String carMiles;

    /**
     * 出售时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date saleTime;

    /**
     * 所属客户id
     */
    private Integer customerId;

    /**
     * 购买状态
     */
    private String purchaseStatus;

    /**
     * 是否投保商业险
     */
    private String isBussiness;

    /**
     * 是否是新增保有客户(1-是)
     */
    private Integer isNewRecord;

    /**
     * 电话号码
     */
    private String customerMobile;

    /**
     * 姓名
     */
    private String customerName;
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
    private Integer age;

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
     * 是否试驾
     */
    private String isDrivingTest;
    /**
     * 备注
     */
    private String remark;
    /**
     *客户生日
     */
    private Date birthday;
    /**
     * 是否完善
     */
    private Integer isDone;
}
