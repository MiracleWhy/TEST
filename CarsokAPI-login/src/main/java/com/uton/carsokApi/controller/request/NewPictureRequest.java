package com.uton.carsokApi.controller.request;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Raytine on 2017/9/7.
 */
public class NewPictureRequest implements Serializable{

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
