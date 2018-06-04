package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Raytine on 2017/11/9.
 */
@Data
public class CarStockPic implements Serializable{

    /**
     * 图片id
     */
    private int id;

    /**
     * 图片地址
     */
    private String picPath;

    /**
     * 图片类型(0:普通图片 1：首图)
     */
    private int type;
}
