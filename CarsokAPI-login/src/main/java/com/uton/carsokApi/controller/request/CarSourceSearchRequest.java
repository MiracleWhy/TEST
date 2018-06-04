package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.util.List;

/**
 * Created by Raytine on 2018/3/13.
 */
@Data
public class CarSourceSearchRequest {

    private  String province;
    private  String city;
    private  String search;
    private List<String> brand;
    private  int rank;
    private  String carSource;
    private  String productionStartDate;
    private  String productionEndDate;
    private int carAge;
    private Integer accountId;
    private Integer childId;
}
