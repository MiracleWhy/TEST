package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.model.CarsokFindCar;
import com.uton.carsokApi.model.CarsokQuote;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangdi on 2018/1/23.
 * desc:
 */
@Data
public class FindCarMsgResponse {
    private int id;
    private String brand;
    private String model;
    private String configuration;
    private String carColour;
    private String createTime;
    private Date firstUpTime;
    private String linkman;
    private String province;
    private String city;
    private String remark;
    List<CarsokQuoteResponse> quoteList =new ArrayList<>();
    private String linkPhone;
    private String indoorType;
}  