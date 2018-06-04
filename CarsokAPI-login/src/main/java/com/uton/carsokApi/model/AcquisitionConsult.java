package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class AcquisitionConsult {
    private Integer id;
    private Integer acquisitionId;
    private BigDecimal consultPrice;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAcquisitionId() {
        return acquisitionId;
    }

    public void setAcquisitionId(Integer acquisitionId) {
        this.acquisitionId = acquisitionId;
    }

    public BigDecimal getConsultPrice() {
        return consultPrice;
    }

    public void setConsultPrice(BigDecimal consultPrice) {
        this.consultPrice = consultPrice;
    }
}
