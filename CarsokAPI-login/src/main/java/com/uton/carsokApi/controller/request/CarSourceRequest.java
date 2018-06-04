package com.uton.carsokApi.controller.request;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.List;

/**
 * Created by zhangdi on 2018/1/18.
 * desc:
 */
@Data
@Log4j
public class CarSourceRequest {
    private  String province;
    private  String city;
    private  String search;
    private List<String> brand;
    private  int rank;
    private  String carSource;
    private  String productionStartDate;
    private  String productionEndDate;
    private int pageSize;
    private int carAge;
    private int pageNum;
    private Integer accountId;
    private Integer childId;


}  