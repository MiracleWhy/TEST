package com.uton.carsokApi.model;

import lombok.Data;
import org.dozer.Mapping;

@Data
public class CustomerDozer {
    private Integer id;
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

    private String before_car;
}
