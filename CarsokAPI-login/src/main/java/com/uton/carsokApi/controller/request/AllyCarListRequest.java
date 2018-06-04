package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by zhangdi on 2018/2/27.
 * desc:
 */
@Data
public class AllyCarListRequest {


    private Integer pageSize;
    private Integer pageNum;
    private String searchBy;

}  