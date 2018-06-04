package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangdi on 2018/1/18.
 * desc:
 */
@Data
public class CarSourceResponse {
    private int productId;
    private String primarypic;
    private String brand;
    private String type;
    private String model;
    private int bargain;
    private String carUrl;
    private String onShelfTime;
    private BigDecimal price;
    private String productionDate;
    private int miles;
/*
    private int isStick;
*/
    private String merchantName;
    private String isMrchantAudit;
    private String productNo;
    private Integer collectId;



}  