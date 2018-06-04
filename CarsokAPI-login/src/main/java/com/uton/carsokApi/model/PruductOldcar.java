package com.uton.carsokApi.model;

import java.util.Date;

public class PruductOldcar {
    private Integer id;

    private Integer pruductId;

    private String cBrand;

    private String cType;

    private String cModel;

    private String province;

    private String city;

    private Integer miles;

    private Date firstUpTime;

    private Integer browseNumTimes;

    private Integer telNumTimes;

    private Date createTime;

    private Date updateTime;

    private Integer browseNumTimesFake;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPruductId() {
        return pruductId;
    }

    public void setPruductId(Integer pruductId) {
        this.pruductId = pruductId;
    }

    public String getcBrand() {
        return cBrand;
    }

    public void setcBrand(String cBrand) {
        this.cBrand = cBrand == null ? null : cBrand.trim();
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType == null ? null : cType.trim();
    }

    public String getcModel() {
        return cModel;
    }

    public void setcModel(String cModel) {
        this.cModel = cModel == null ? null : cModel.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getMiles() {
        return miles;
    }

    public void setMiles(Integer miles) {
        this.miles = miles;
    }

    public Date getFirstUpTime() {
        return firstUpTime;
    }

    public void setFirstUpTime(Date firstUpTime) {
        this.firstUpTime = firstUpTime;
    }

    public Integer getBrowseNumTimes() {
        return browseNumTimes;
    }

    public void setBrowseNumTimes(Integer browseNumTimes) {
        this.browseNumTimes = browseNumTimes;
    }

    public Integer getTelNumTimes() {
        return telNumTimes;
    }

    public void setTelNumTimes(Integer telNumTimes) {
        this.telNumTimes = telNumTimes;
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

    public Integer getBrowseNumTimesFake() {
        return browseNumTimesFake;
    }

    public void setBrowseNumTimesFake(Integer browseNumTimesFake) {
        this.browseNumTimesFake = browseNumTimesFake;
    }
}