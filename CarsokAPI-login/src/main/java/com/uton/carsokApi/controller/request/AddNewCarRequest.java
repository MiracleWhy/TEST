package com.uton.carsokApi.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddNewCarRequest implements Serializable {

    /**
     * 品牌
     */
    @NotNull(message = "品牌不能为空")
    private String carBrand;
    /**
     * 车型
     */
    @NotNull(message = "车型不能为空")
    private String carSeries;
    /**
     * vin码
     */
    @NotNull(message = "vin码不能为空")
    private String vin;
    /**
     * 配置
     */
    @NotNull(message = "配置不能为空")
    private String configuration;
    /**
     * 排量
     */
    private String displacement;
    /**
     * 座位数
     */
    private Integer seatNumber;
    /**
     * 变速箱
     */
    private String ariableBox;
    /**
     * 排放标准
     */
    private String exhaust;
    /**
     * 生产日期
     */
    private Date productionDate;
    /**
     * 入库日期
     */
    @NotNull(message = "入库日期不能为空")
    private Date instoreDate;
    /**
     * 购车类型 （金融公司、自有资金、厂家金融、其他）
     */
    private String buyType;
    /**
     * 车况描述 （完好、质损）
     */
    private String condition;
    /**
     * 进价
     */
    private BigDecimal miniprice;
    /**
     * 指导价
     */
    @NotNull(message = "指导价不能为空")
    private BigDecimal price;
    /**
     * 车型亮点
     */
    private String productDescr;
    /**
     * 账户id
     */
    private Integer accountId;
    /**
     * 车辆id
     */
    @NotNull(message = "车辆id不能为空")
    private Integer carId;


}
