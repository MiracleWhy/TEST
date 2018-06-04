package com.uton.carsokApi.controller.request;

import com.uton.carsokApi.controller.response.CarName;
import lombok.Data;

import java.util.List;

/**
 * Created by SEELE on 2017/12/18.
 */
@Data
public class TestDriveRequest {

    private int custId;

    //    跟进信息
    private String customerFlowMessage;

    //    满意度
    private String satisfaction;

    //    试驾时长
    private String TDtime;

    //试驾车型
    private List<CarName> list;

    private int account_id;

    private int child_id;
}
