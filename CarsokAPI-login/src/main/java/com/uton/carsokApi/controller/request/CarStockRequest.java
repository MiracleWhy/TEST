package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raytine on 2017/11/8.
 */
@Data
public class CarStockRequest implements Serializable {

    /**
     * 车型库id
     */
    private Integer id;

    /**
     * 主图加附图图片列表
     */
    private List<CarStockPicRequest> picList;

    /**
     * 附图图片列表
     */
    private String[] picLists;

    /**
     * 主图
     */
    private String mainPic;

    /**
     * 品牌
     */
    private String carBrand;

    /**
     * 品牌id
     */
    private Integer carBrandId;

    /**
     * 车型
     */
    private String carSeries;

    /**
     * 所属人
     */
    private String accountId;

}
