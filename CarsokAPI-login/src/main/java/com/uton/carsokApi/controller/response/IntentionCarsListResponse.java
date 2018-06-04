package com.uton.carsokApi.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangdi on 2017/12/4.
 * desc:
 */
@Data
@Log4j
public class IntentionCarsListResponse {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer carId;
    private String brand;
    private String series;
    private String model;
    private String picPath;
    private String onShelfTime;
    private BigDecimal price;
    private String productionDate;
    private String vinSub;
    private BigDecimal miniprice;
    private Integer miles;
    private List<CarStockPic> picList;
    private String productUrl;
    private Integer accountId;
    private String merchantName;
    private Integer browseNumTimes;
    private Integer telNumTimes;
    private Integer onShelfDays;
    private String productNo;
    private Integer isMerchantAudit;


}  