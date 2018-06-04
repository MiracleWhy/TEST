package com.uton.carsokApi.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangdi on 2018/2/27.
 * desc:
 */
@Data
public class AllyCarListResponse  {

    private String merchantName;
    private String isMerchantAudit;
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
    private Integer miles;


}  