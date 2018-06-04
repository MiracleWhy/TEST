package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by Raytine on 2017/12/22.
 */
@Data
public class CarCollectRequest {

    /**
     * 搜索关键字
     */
    private String searchName;
    /**
     * 主账号id
     */
    private Integer accountId;

    /**
     * 子账号id
     */
    private Integer childId;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;
}
