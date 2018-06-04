package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Raytine on 2017/11/8.
 */
@Data
public class CarStockPicRequest implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 图片地址
     */
    private String picPath;

    /**
     * 图片类型 1：首图  0：普通图片
     */
    private Integer type;
}
