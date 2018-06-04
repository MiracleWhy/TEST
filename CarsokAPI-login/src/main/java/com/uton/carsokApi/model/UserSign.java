package com.uton.carsokApi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UserSign implements Serializable {
    private Integer id;

    private String accountId;

    private Date lastSignDate;

    private Integer continuityTimes;

    private Integer presentTimes;

    private Integer enable;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getLastSignDate() {
        return lastSignDate;
    }

    public void setLastSignDate(Date lastSignDate) {
        this.lastSignDate = lastSignDate;
    }

    public Integer getContinuityTimes() {
        return continuityTimes;
    }

    public void setContinuityTimes(Integer continuityTimes) {
        this.continuityTimes = continuityTimes;
    }

    public Integer getPresentTimes() {
        return presentTimes;
    }

    public void setPresentTimes(Integer presentTimes) {
        this.presentTimes = presentTimes;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
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