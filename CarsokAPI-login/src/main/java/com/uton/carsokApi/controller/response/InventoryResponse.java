package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Raytine on 2017/11/9.
 */
@Data
public class InventoryResponse implements Serializable{

    /**
     *车型库id
     */
    private int id;

    /**
     *品牌
     */
    private String carBrand;

    /**
     *车型
     */
    private String carSeries;

    /**
     *vin
     */
    private String vin;

    /**
     *车型首图
     */
    private String picPath;

    /**
     *	配置
     */
    private String configuration;

    /**
     *入库日期
     */
    private String instoreDate;

    /**
     *指导价
     */
    private String price;

    /**
     *车辆分享链接
     */
    private String carUrl;

    /**
     *图片列表
     */
    private List<CarStockPic> picList;

    /**
     * 车型库id
     */
    private int carId;

    /**
     * 车辆编号
     */
    private String productNo;

    /**
     * 排量
     */
    private String displacement;

    /**
     * 座位数
     */
    private String seatNumber;

    /**
     * 变速箱
     */
    private String ariableBox;

    /**
     * 排放标准
     */
    private String exhaust;

    /**
     *车辆描述
     */
    private String productDescr;

    /**
     * 生产日期
     */
    private String productionDate;

    /**
     * 车辆名称
     */
    private String productName;

}
