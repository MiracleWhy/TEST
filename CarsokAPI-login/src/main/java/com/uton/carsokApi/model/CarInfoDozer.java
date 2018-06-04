package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

public class CarInfoDozer {
    /**
     * 车辆名称
     */

    private String tenureCarname;
    /**
     * 车辆vin码
     */

    private String tenureVin;
    /**
     * 出售方式
     */

    private Integer tenureCartype;
    /**
     * 交强险到期日
     */

    private Date tenureCompulsory;
    /**
     * 商业险到期日
     */

    private Date tenureBusiness;
    /**
     * 保养到期日
     */

    private Date tenureMaintain;
    /**
     * 质保到期日
     */

    private Date tenureWarranty;
    /**
     * 价格
     */

    private BigDecimal tenureCarprice;

    /**
     * product  ID
     */
    private Integer productId;
    /**
     * 行驶里程
     */

    private String carMiles;
    /**
     * 出售时间
     */

    private Date saleTime;

    private Integer accountId;

    /**
     * childId
     */

    private Integer childId;
    /**
     * 购买状态
     */
    private String purchaseStatus;
    /**
     * 是否投保商业险
     */

    private String isBussiness;
    /**
     * 是否试驾
     */

    private String isDrivingTest;

}
