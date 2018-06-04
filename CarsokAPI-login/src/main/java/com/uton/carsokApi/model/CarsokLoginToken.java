package com.uton.carsokApi.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/22.
 */
@Data
public class CarsokLoginToken {
    private Integer id;
    private String accounts;
    private String token;
    private Date loginTime;
    private Date loginOutTime;
    private String ipAddress;
    private String addressMsg;
}
