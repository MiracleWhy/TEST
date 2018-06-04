package com.uton.carsokApi.controller.response;

import lombok.Data;

/**
 * Created by Raytine on 2017/12/22.
 */
@Data
public class AccountCollectListResponse {

    /**
     * 车行收藏表id
     */
    private Integer id;

    /**
     * 车行名称
     */
    private String merchantName;

    /**
     * 是否商家认证 1：否 2：是
     */
    private Integer isMerchantAudit;

    /**
     * 商家主图
     */
    private String picPath;

    /**
     * 注册省
     */
    private String province;

    /**
     * 注册市
     */
    private String city;

    /**
     * 商家编号
     */
    private String accountCode;

    /**
     * 商家微店地址
     */
    private String accountUrl;

    /**
     * 收藏车行id
     */
    private Integer collectAccountId;

    /**
     * 是否置顶（0：置顶  1：未置顶）
     */
    private Integer isTofront;
}
