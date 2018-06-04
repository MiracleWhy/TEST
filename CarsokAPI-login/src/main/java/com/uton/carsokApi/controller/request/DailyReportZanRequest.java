package com.uton.carsokApi.controller.request;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/18.
 */
public class DailyReportZanRequest {
    private Integer id;
    private String reportType;
    private String fabulousPhone;
    private String getfabulousPhone;
    private String departmentType;
    private Integer carId;
    private String childPhone;
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

    public String getGetfabulousPhone() {
        return getfabulousPhone;
    }

    public void setGetfabulousPhone(String getfabulousPhone) {
        this.getfabulousPhone = getfabulousPhone;
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

    public String getChildPhone() {
        return childPhone;
    }

    public void setChildPhone(String childPhone) {
        this.childPhone = childPhone;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
