package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
@Data
public class LatentCustomerRequest {
    private Integer times;
    private List<String> level;//客户级别
    private List<String> budget;//购买预算
    private List<Integer> childId;//销售归属
    private List<String> source;//信息来源
    private Integer type;//tab类型
    private String select;//搜索关键字
    private Integer pageSize;
    private Integer pageCount;
}
