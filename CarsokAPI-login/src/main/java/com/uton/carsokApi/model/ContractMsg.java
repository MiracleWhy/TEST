package com.uton.carsokApi.model;

import lombok.Data;

/**
 * Created by zhangdi on 2018/2/28.
 * desc:
 */
@Data
public class ContractMsg {

    private String contractUrl;
    private String contractNum;
    private int carId;
    private int contractType;
    private int enable;
}  