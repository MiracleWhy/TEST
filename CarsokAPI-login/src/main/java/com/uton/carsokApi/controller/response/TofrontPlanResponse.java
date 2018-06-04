package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Raytine on 2018/1/19.
 */
@Data
public class TofrontPlanResponse implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 套餐名称
     */
    private String planName;

    /**
     * 套餐价钱
     */
    private BigDecimal planPrice;

    /**
     * 套餐时效（天）
     */
    private Integer planTime;
}
