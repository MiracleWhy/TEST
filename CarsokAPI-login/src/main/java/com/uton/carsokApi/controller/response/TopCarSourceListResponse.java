package com.uton.carsokApi.controller.response;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.math.BigDecimal;

/**
 * Created by zhangdi on 2018/1/22.
 * desc:
 */
@Data
@Log4j
public class TopCarSourceListResponse {

    private int id;
    private String brand;
    private String type;
    private String model;
    private String onShelfTime;
    private BigDecimal price;
    private BigDecimal minPrice;
    private String firstUpTime;
    private String vin;
    private int browseNumTimes;
    private int telNumTimes;
    private int saleDays;
    private String picPath;
    private String miles;

}  