package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

public class AcountWithdrawCash {
    private Integer id;

    private Integer accountId;

    private String realName;

    private String bankNum;

    private String openedBank;

    private BigDecimal amount;

    private Short status;

    private String withdrawNum;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum == null ? null : bankNum.trim();
    }

    public String getOpenedBank() {
        return openedBank;
    }

    public void setOpenedBank(String openedBank) {
        this.openedBank = openedBank == null ? null : openedBank.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(String withdrawNum) {
        this.withdrawNum = withdrawNum == null ? null : withdrawNum.trim();
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