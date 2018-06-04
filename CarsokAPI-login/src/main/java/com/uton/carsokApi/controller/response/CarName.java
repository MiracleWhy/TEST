package com.uton.carsokApi.controller.response;

import lombok.Data;
import lombok.extern.log4j.Log4j;

/**
 * Created by zhangdi on 2017/11/10.
 * desc:
 */

@Data
@Log4j
public class CarName {

    private String model;

    private String brand;

    private String series;
    //0:车系 1:意向车辆
    private Integer productStatus;
    //0:库存 1:联盟
    private Integer productType;
    //车辆id
    private Integer productId;
    //车辆商家id
    private Integer merchantId;


}  