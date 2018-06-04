package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class CustomerFlow {
    private Integer id;
    private Integer customerId;
    private String customerFlowMessage;
    private Date createTime;
    private Integer status;
    private Date pushTime;
    private Integer custCome;
    private Integer accountId;
    private Integer childId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getCustCome() {
        return custCome;
    }

    public void setCustCome(Integer custCome) {
        this.custCome = custCome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFlowMessage() {
        return customerFlowMessage;
    }

    public void setCustomerFlowMessage(String customerFlowMessage) {
        this.customerFlowMessage = customerFlowMessage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
