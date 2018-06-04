package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/22.
 * 订单转移记录
 */
public class TransferAscription {

    private Integer id;//自增id
    private Integer transferId;//转移订单id
    private String transferBefore;//转移前人员电话
    private String transferAfter;//转移后人员电话
    private String transferType;//转移订单类型
    private Date createTime;//转移时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public String getTransferBefore() {
        return transferBefore;
    }

    public void setTransferBefore(String transferBefore) {
        this.transferBefore = transferBefore;
    }

    public String getTransferAfter() {
        return transferAfter;
    }

    public void setTransferAfter(String transferAfter) {
        this.transferAfter = transferAfter;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
