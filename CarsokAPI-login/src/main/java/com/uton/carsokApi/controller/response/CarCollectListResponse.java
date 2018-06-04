package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Raytine on 2017/12/22.
 */
@Data
public class CarCollectListResponse {

    /**
     * 车辆id
     */
    private Integer carId;
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String productName;

    /**
     * 指导价
     */
    private BigDecimal price;

    /**
     * 首次上牌时间
     */
    private String firstUpTime;

    /**
     * 车辆里程
     */
    private String miles;

    /**
     * 上架时间
     */
    private String onShelfTime;

    /**
     * 车行编号
     */
    private String accountCode;

    /**
     * 是否认证（1：否 2：是）
     */
    private Integer isMerchantAudit;

    /**
     * 车行名称
     */
    private String merchantName;

    /**
     *砍价商家数量
     */
    private int bargainCount;

    /**
     * 车辆详情页地址
     */
    private String carUrl;

    /**
     * 车辆编号
     */
    private String productNo;

    /**
     * 是否置顶（0：置顶  1：未置顶）
     */
    private Integer isTofront;

    /**
     * 车辆首图
     */
    private String picPath;
}
