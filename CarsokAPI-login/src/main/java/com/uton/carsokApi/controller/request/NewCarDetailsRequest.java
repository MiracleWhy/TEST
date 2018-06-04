package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Raytine on 2017/11/9.
 */
@Data
public class NewCarDetailsRequest implements Serializable{

    /**
     * id
     */
    private int id;

    /**
     *vin
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
    private String  exhaust;

    /**
     *生产日期
     */
    private Date productionDate;

    /**
     *入库日期
     */
    private Date instoreDate;

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
    private BigDecimal miniprice;

    /**
     *指导价
     */
    private BigDecimal price;

    /**
     *车型亮点
     */
    private String productDescr;
}
