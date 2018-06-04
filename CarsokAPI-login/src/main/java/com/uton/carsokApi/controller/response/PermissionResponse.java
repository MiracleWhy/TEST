package com.uton.carsokApi.controller.response;

/**
 * Created by Administrator on 2017/10/30.
 */
public class PermissionResponse {
    private Integer id;
    private Integer childId;
    private String powerName;
    private String powerValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerValue() {
        return powerValue;
    }

    public void setPowerValue(String powerValue) {
        this.powerValue = powerValue;
    }
}
