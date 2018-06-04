package com.uton.carsokApi.controller.request;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/25.
 */
public class ModularApplyRequest {
    private Integer id;
    private Integer accountId;
    private String type;
    private Integer applyIf;
    private Date applyTime;
    private Date lastUseTime;
    private Date createTime;
    private Date updateTime;
    private String contactName;
    private String contactMobile;
    private Integer merchantSize;
    private Integer employeeNum;
    private String merchantAddress;

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getApplyIf() {
        return applyIf;
    }

    public void setApplyIf(Integer applyIf) {
        this.applyIf = applyIf;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public Integer getMerchantSize() {
        return merchantSize;
    }

    public void setMerchantSize(Integer merchantSize) {
        this.merchantSize = merchantSize;
    }

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }
}
