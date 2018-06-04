package com.uton.carsokApi.controller.request;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */
public class PermissionRequest {
    private List<String> roleNames;
    private String childMobile;
    private Integer powerStatus;

    public Integer getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(Integer powerStatus) {
        this.powerStatus = powerStatus;
    }

    public String getChildMobile() {
        return childMobile;
    }

    public void setChildMobile(String childMobile) {
        this.childMobile = childMobile;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
