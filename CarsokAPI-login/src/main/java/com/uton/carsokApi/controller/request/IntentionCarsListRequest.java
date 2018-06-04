package com.uton.carsokApi.controller.request;

import lombok.Data;
import lombok.extern.log4j.Log4j;

/**
 * Created by zhangdi on 2017/12/4.
 * desc:
 */
@Data
@Log4j
public class IntentionCarsListRequest {

    private int pageSize;

    private int pageNum;

    private int carType;//0:库存  1:联盟

    private int carAge;//车龄

    private String searchKey;//搜索关键字

    private int sorts;//排序方式

    private  String productionStartDate;
    private  String productionEndDate;

}  