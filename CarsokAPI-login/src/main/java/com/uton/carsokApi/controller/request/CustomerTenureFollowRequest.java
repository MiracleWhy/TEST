package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.util.Date;
@Data
public class CustomerTenureFollowRequest {
    /**
     *s
     */
    private Integer id;
    /**
     *保有车Id
     */
    private Integer tenurecarId;
    /**
     * 回访信息
     */
    private String followMessage;
    /**
     * 1:初次购买 2:3日电话回访 3:7日电话回访
     */
    private Integer followType;
    /**
     * accountId
     */
    private Integer accountId;
    /**
     * childId
     */
    private Integer childId;

}
