package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Raytine on 2018/1/23.
 */
@Data
public class AccountTofrontResponse implements Serializable {

    /**
     * 置顶表id
     */
    private Integer id;

    /**
     * 车行名称
     */
    private String mechantName;

    /**
     * 车行编号
     */
    private String accountCode;

    /**
     * 店招图地址
     */
    private String merchanImagePath;

    /**
     * 套餐名称
     */
    private String planName;

    /**
     * 套餐开始时间
     */
    private String startTime;

    /**
     * 套餐结束时间
     */
    private String endTime;

    /**
     * 是否有效数据（0：有效（显示置顶标志）  1：无效（不显示置顶标志））
     */
    private Integer enable;

    /**
     * 商铺详情页地址
     */
    private String accountUrl;
}
