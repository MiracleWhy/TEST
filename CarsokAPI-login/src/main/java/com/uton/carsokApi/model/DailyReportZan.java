package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/17.
 */
public class DailyReportZan {
    private Integer id;
    private String reportType;
    private String fabulousPhone;
    private String accountPhone;
    private String departmentType;
    private Integer carId;
    private String getfabulousPhone;
    private Date createTime;
    private Date updateTime;
    private Integer enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFabulousPhone() {
        return fabulousPhone;
    }

    public void setFabulousPhone(String fabulousPhone) {
        this.fabulousPhone = fabulousPhone;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getGetfabulousPhone() {
        return getfabulousPhone;
    }

    public void setGetfabulousPhone(String getfabulousPhone) {
        this.getfabulousPhone = getfabulousPhone;
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
