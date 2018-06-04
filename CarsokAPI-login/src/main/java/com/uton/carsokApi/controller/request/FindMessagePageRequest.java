package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 *
 */
@Data
public class FindMessagePageRequest {
    /**
     *外部id(例如潜在客户id,保有车辆id)
     */
    int out_id;
    /**
     *所属模块(1-潜客管理,2-保有客户管理)
     */
    int model;
    /**
     *类型
     */
    String type;
    /**
     *分页参数，第几页(从1开始)
     */
    int pageNum;
    /**
     *分页参数，每页显示条数
     */
    int pageSize;
}
