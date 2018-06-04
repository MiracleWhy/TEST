package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarStockListRequest implements Serializable {

    /**
     * 搜索字段 否
     */
    private String searchBy;
    /**
     * 品牌 否
     */
    private Integer carBrandId;
    /**
     * 车型 否
     */
    private Integer id;
    /**
     * 分页参数，第几页 是
     */
    private int pageNum;
    /**
     * 分页参数，每页显示条数 是
     */
    private int pageSize;
    /**
     * 账号
     */
    private String accountId;


}
