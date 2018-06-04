package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Raytine on 2017/12/25.
 */
@Data
public class CarsokQuoteDetailsResponse {

    /**
     * id
     */
    private Integer id;
    /**
     * 联系人
     */
    private String name;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 意向价格
     */
    private BigDecimal intentionalPrice;
    /**
     * 备注
     */
    private String remark;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 车型
     */
    private String model;
    /**
     * 配置
     */
    private String configuration;
    /**
     * 发布时间
     */
    private String createTime;
    /**
     * 颜色
     */
    private String carColour;
    /**
     * 上牌省
     */
    private String province;
    /**
     * 上牌市
     */
    private String city;
    /**
     * 报价商家数量
     */
    private Integer quoteCount;
    /**
     * 品牌logo
     */
    private String brandLogo;

    /**
     * 内饰
     */
    private String indoorType;
}
