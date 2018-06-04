package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/23.
 */
public class ModularApply {
    private Integer id;
    private Integer accountId;
    private String type;
    private Integer applyIf;
    private Date applyTime;
    private Date lastUseTime;
    private Date createTime;
    private Date updateTime;
    private String contact_name;
    private String contact_mobile;
    private String company_name;
    private Integer merchant_size;
    private Integer employee_num;

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
}
