package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class AcquisitionCarPricing {
    private Integer id;
    private String pricingInfo;
    private Integer acquisitionCarId;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPricingInfo() {
        return pricingInfo;
    }

    public void setPricingInfo(String pricingInfo) {
        this.pricingInfo = pricingInfo;
    }

    public Integer getAcquisitionCarId() {
        return acquisitionCarId;
    }

    public void setAcquisitionCarId(Integer acquisitionCarId) {
        this.acquisitionCarId = acquisitionCarId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
