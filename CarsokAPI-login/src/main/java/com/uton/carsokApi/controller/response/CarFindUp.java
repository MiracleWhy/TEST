package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.util.Date;

/**
 * Created by SEELE on 2017/12/25.
 */
@Data
public class CarFindUp {
//    id
    private int id;
//    车牌标识
    private String primarypic;
//    品牌
    private String brand;
//    车型
    private String model;
//    配置
    private String configuration;
//    发布时间
    private String createTime;
//    颜色
    private String carColour;
//    报价信息
    private int quoteNum;
//    上牌省
    private String province;
//    上牌市
    private String city;
//内饰
    private String indoorType;
}
