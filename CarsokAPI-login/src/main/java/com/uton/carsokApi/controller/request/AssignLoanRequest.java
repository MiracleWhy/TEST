package com.uton.carsokApi.controller.request;

import lombok.Data;

/**
 * Created by SEELE on 2017/11/15.
 */
@Data
public class AssignLoanRequest {
        private String custId;

        private String customerFlowMessage;
//        是否热心
        private String enthusiasm;
//        是否专心
        private String concentrate;
//        是否回访
        private String visit;

        private String account_id;

        private String child_id;
}
