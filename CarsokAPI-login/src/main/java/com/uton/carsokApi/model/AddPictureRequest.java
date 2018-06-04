package com.uton.carsokApi.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SEELE on 2017/9/12.
 */
public class AddPictureRequest implements Serializable {

    /**
     * 图片地址
     */
    private List<String> picUrl;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 商品id
     */
    private Integer productId;

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
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
}
