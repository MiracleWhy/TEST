package com.uton.carsokApi.controller.response;

/**
 * Created by Raytine on 2017/9/7.
 */
public class NewPictureResponse {
    /**
     *  图片id
     */
    private Integer id;
    /**
     * 图片名称
     */
    private String picName;

    /**
     * 图片地址
     */
    private String picUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
