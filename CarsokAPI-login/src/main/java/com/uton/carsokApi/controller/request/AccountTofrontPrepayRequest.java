package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by zhangdi on 2018/1/29.
 * desc:
 */
@Data
public class AccountTofrontPrepayRequest {


    private BigDecimal total_fee;
    private Integer tofrontPlanId;
    private Integer tofrontAccountId;
    private Integer accountId;
    private Integer childId;

}  