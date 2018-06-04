package com.uton.carsokApi.controller.request;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/6/20.
 */
public class DRReceptionResponse implements Serializable{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getRecpCount() {
        return recpCount;
    }

    public void setRecpCount(String recpCount) {
        this.recpCount = recpCount;
    }

    public String getReceperPhone() {
        return receperPhone;
    }

    public void setReceperPhone(String receperPhone) {
        this.receperPhone = receperPhone;
    }


    private String name;
    private String roleName;
    private String childId;
    private String recpCount;
    private Integer id;
    private Boolean accountZanIf;
    private Boolean zjlZanIf;
    private Integer zanId;

    public Integer getZanId() {
        return zanId;
    }

    public void setZanId(Integer zanId) {
        this.zanId = zanId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    private String receperPhone;
}
