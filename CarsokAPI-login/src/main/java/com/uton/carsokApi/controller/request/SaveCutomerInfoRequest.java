package com.uton.carsokApi.controller.request;

import lombok.Data;

@Data
public class SaveCutomerInfoRequest {
    /**
     *保有客户id
     */
    private int cutomerId;
    /**
     *电话号码
     */
    private String customerMobile;
    /**
     *客户姓名
     */
    private String customerName;
    /**
     *性别
     */
    private String sex;
    /**
     *来源
     */
    private String source;
    /**
     *年龄
     */
    private String age;
    /**
     *住址
     */
    private String address;
    /**
     *职业
     */
    private String job;
    /**
     *性格
     */
    private String personality;
    /**
     *人脉
     */
    private String link;
    /**
     *购车之前车型
     */
    private String before_car;
    /**
     *备注
     */
    private String remark;
}
