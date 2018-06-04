package com.uton.carsokApi.controller.request;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.List;

/**
 * Created by zhangdi on 2017/11/10.
 * desc:
 */
@Log4j
@Data
public class UpdateCustCarMsgRequest {

    private Integer custId;

    private List<Integer> productId;

    private Integer taskId;

}