package com.uton.carsokApi.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangdi on 2018/1/22.
 * desc:
 */
@Data
public class MerchantListResponse implements Serializable {
    private int id;
    private String isMerchantAudit;
    private String province;
    private String city;
    private String merchantName;
//    private int isStick;
    private  String merchantImagePath;
    private String accountCode;
    private String storeUrl;
    private Integer collectId;

}