package com.uton.carsokApi.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TenureFindListResponse {
    private int tenureCarId;
    private String tenureCarName;
    private String saleTime;
    private String customerName;
    private String customerMobile;
    private String showType;
}
