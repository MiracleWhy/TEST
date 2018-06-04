package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Raytine on 2017/11/9.
 */
@Data
public class InventoryRequest implements Serializable{

    /**
     *搜索字段
     */
    private String searchBy;

    /**
     *品牌
     */
    private String carBrand;

    /**
     *车型
     */
    private String carSeries;

    /**
     *生产日期开始时间 （格式：2017-11-12）
     */
    private String productionStartDate;

    /**
     *生产日期结束时间 （格式：2017-11-12）
     */
    private String productionEndDate;

    /**
     *入库日期开始时间 （格式：2017-11-12）
     */
    private String instoreStartDate;

    /**
     *入库日期结束时间 （格式：2017-11-12）
     */
    private String instoreEndDate;

    /**
     *车况
     */
    private String condition;

    /**
     *指导价（排序 1：高到低 2:低到高）
     */
    private int price;

    /**
     *库龄（排序 1：高到低 2:低到高）
     */
    private int instoreDate;

    /**
     *分页参数，第几页
     */
    private int pageNum;

    /**
     *分页参数，每页显示条数
     */
    private int pageSize;

    /**
     * 所属人（登录人）
     */
    private String accountId;

    /**
     * 销售字段
     */
    private int saleStatus;

    /**
     * 是否潜客页面进入（标识位）
     */
    private String proCustomer;

}
