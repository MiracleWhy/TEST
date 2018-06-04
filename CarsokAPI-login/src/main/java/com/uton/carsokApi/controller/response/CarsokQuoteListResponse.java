package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.util.Date;

/**
 * Created by Raytine on 2017/12/23.
 */
@Data
public class CarsokQuoteListResponse {

    /**
     *  报价消息表id
     */
    private Integer quoteId;

    /**
     * 联系人
     */
    private String name;

    /**
     * 时间
     */
    private Date createTime;
}
