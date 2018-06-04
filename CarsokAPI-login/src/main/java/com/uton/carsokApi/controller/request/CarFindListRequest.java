package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by SEELE on 2017/12/25.
 */
@Data
public class CarFindListRequest {
//    	销售省
    private String province;
//      销售市
    private String city;
//      输入品牌或车系或配置
    private String search;
//      页数
    private int pageNum;
//      一页几条
    private int pageSize;
}
