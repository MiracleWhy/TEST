package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by zhangdi on 2018/1/22.
 * desc:
 */
@Data
public class MerchantListRequest {

    private String search;
    private String province;
    private String city;
    private int pageNum;
    private int pageSize;
    private Integer accountId;
    private Integer childId;

}  