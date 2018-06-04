package com.uton.carsokApi.model;

import java.util.Date;

public class AcountPos {
    private Integer id;

    private Integer accountId;

    private Integer accountCode;

    private String posLoginAccount;

    private String posLoginPasswd;

    private String posSn;

    private Short applyStatus;

    private Short openStatus;

    private Date createTime;

    private Date updateTime;

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

    public Integer getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Integer accountCode) {
        this.accountCode = accountCode;
    }

    public String getPosLoginAccount() {
        return posLoginAccount;
    }

    public void setPosLoginAccount(String posLoginAccount) {
        this.posLoginAccount = posLoginAccount == null ? null : posLoginAccount.trim();
    }

    public String getPosLoginPasswd() {
        return posLoginPasswd;
    }

    public void setPosLoginPasswd(String posLoginPasswd) {
        this.posLoginPasswd = posLoginPasswd == null ? null : posLoginPasswd.trim();
    }

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn == null ? null : posSn.trim();
    }

    public Short getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Short applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Short getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Short openStatus) {
        this.openStatus = openStatus;
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
}