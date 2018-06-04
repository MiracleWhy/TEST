package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Raytine on 2018/1/23.
 */
@Data
public class CarTofrontResponse implements Serializable {
    /**
     * 置顶表id
     */
    private Integer id;

    /**
     * 车辆名称
     */
    private String productName;

    /**
     * 车辆编号
     */
    private String productNo;

    /**
     * 套餐开始时间
     */
    private String startTime;

    /**
     * 套餐名称
     */
    private String planName;

    /**
     * 是否有效数据（0：有效（显示置顶标志）  1：无效（不显示置顶标志））
     */
    private Integer enable;

    /**
     * 车辆详情页
     */
    private String carUrl;
}
