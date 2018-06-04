package com.uton.carsokApi.controller.request;

import lombok.Data;
import lombok.extern.log4j.Log4j;

/**
 * Created by zhangdi on 2018/1/22.
 * desc:
 */
@Data
@Log4j
public class TopCarSourceListRequest {

    private int  rank;
    private String  productionStartDate;
    private String  productionEndDate;
    private int  pageSize;
    private int  pageNum;
    private int accountId;
}  