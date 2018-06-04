package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Raytine on 2017/9/12.
 */
public class AddNextVisit {
    private String id;
    private String followupPerson;
    private String integrationPerson;
    private String message;
    private String bussinesId;
    private String address;
    private Date createTime;
    private Date updateTime;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFollowupPerson() {
        return followupPerson;
    }

    public void setFollowupPerson(String followupPerson) {
        this.followupPerson = followupPerson;
    }

    public String getIntegrationPerson() {
        return integrationPerson;
    }

    public void setIntegrationPerson(String integrationPerson) {
        this.integrationPerson = integrationPerson;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBussinesId() {
        return bussinesId;
    }

    public void setBussinesId(String bussinesId) {
        this.bussinesId = bussinesId;
    }
}
