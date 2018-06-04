package com.uton.carsokApi.controller.response;

import lombok.Data;

@Data
public class CarsokTenureCarResponse {
    /**
     * 车辆名称
     */
    private String tenureCarname;
    /**
     * 出售时间
     */
    private long saleTime;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 是否完善
     */
    private String isDone;


}
