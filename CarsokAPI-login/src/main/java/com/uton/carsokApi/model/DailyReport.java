package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/19.
 */
public class DailyReport {
    private Integer id;
    private String result;
    private String reporter;
    private String reporterName;
    private String title;
    private Integer isRead;
    private Date reportDate;
    private Date createTime;
    private Date updateTime;
    private Integer enable;
    private String childPhone;
    private String roleName;
    private Boolean accountZanIf;//主帐号是否赞
    private Boolean zjlZanIf;//总经理是否赞
    private Integer zanId;

    public Integer getZanId() {
        return zanId;
    }

    public void setZanId(Integer zanId) {
        this.zanId = zanId;
    }

    public Boolean getAccountZanIf() {
        return accountZanIf;
    }

    public void setAccountZanIf(Boolean accountZanIf) {
        this.accountZanIf = accountZanIf;
    }

    public Boolean getZjlZanIf() {
        return zjlZanIf;
    }

    public void setZjlZanIf(Boolean zjlZanIf) {
        this.zjlZanIf = zjlZanIf;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getChildPhone() {
        return childPhone;
    }

    public void setChildPhone(String childPhone) {
        this.childPhone = childPhone;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
