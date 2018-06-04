package com.uton.carsokApi.controller.request;

import java.util.Date;

/**
 * Created by Raytine on 2017/9/7.
 */
public class DatumManageRequest {

    /**
     * 账号
     */
    private String accountId;

    /**
     * 销售状态
     */
    private String saleStatus;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 产品类型
     */
    private String productName;

    /**
     * 月份
     * @return
     */
    private String mouth;

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
