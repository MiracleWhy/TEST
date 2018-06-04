package com.uton.carsokApi.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Raytine on 2017/11/9.
 */
@Data
public class CarDetailsResponse implements Serializable {

    /**
     * 车辆id
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
     *vin码
     */
    private String vin;

    /**
     *配置
     */
    private String configuration;

    /**
     *排量
     */
    private String displacement;

    /**
     *座位数
     */
    private int seatNumber;

    /**
     *变速箱
     */
    private String ariableBox;

    /**
     *排放标准
     */
    private String exhaust;

    /**
     *生产日期
     */
    private String productionDate;

    /**
     *入库日期
     */
    private String instoreDate;

    /**
     *购车类型 （金融公司、自有资金、厂家金融、其他）
     */
    private String buyType;

    /**
     *车况描述 （完好、质损）
     */
    private String condition;

    /**
     *进价
     */
    private String miniprice;

    /**
     *指导价
     */
    private String price;

    /**
     *车型亮点
     */
    private String productDescr;

    /**
     * 车辆在售状态（0： 在售  1： 已售）
     */
    private int saleStatus;

    /**
     *图片列表
     */
    private List<CarStockPic> picList;

    /**
     * 车辆详情地址
     */
    private String carDetailUrl;

    /**
     * productNo
     */
    private String productNo;
}
