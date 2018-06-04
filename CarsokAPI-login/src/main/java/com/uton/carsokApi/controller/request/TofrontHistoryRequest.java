package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by Raytine on 2018/1/24.
 */
@Data
public class TofrontHistoryRequest {

    /**
     * 分页参数 页码
     */
    private Integer pageNum;

    /**
     * 分页参数 条数
     */
    private Integer pageSize;
}
