package com.uton.carsokApi.controller.request;

import lombok.Data;

@Data
public class CreateCarServeInfoRequest {
    /**
     * 保有车辆ID
     */
    private String tenureCarId;
    /**
     * 主账号ID
     */
    private int accountId;
    /**
     * 子账号ID
     */
    private int childId;
}
