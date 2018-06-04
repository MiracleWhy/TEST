package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by zhangdi on 2018/1/25.
 * desc:
 */
@Data
public class CarTofrontPrepayRequest {

    private BigDecimal total_fee;
    private Integer tofrontPlanId;
    private Integer tofrontProductId;
    private Integer accountId;
    private Integer childId;

}  