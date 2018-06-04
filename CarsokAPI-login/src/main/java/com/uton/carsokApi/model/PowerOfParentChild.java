package com.uton.carsokApi.model;

/**
 * Created by Administrator on 2017/10/31.
 */
public class PowerOfParentChild {
    private Integer id;
    private String childPowerName;
    private String childPowerValue;
    private String parentPowerName;
    private Integer powerStatus;
    private Integer powerIf;

    public Integer getPowerIf() {
        return powerIf;
    }

    public void setPowerIf(Integer powerIf) {
        this.powerIf = powerIf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChildPowerName() {
        return childPowerName;
    }

    public void setChildPowerName(String childPowerName) {
        this.childPowerName = childPowerName;
    }

    public String getChildPowerValue() {
        return childPowerValue;
    }

    public void setChildPowerValue(String childPowerValue) {
        this.childPowerValue = childPowerValue;
    }

    public String getParentPowerName() {
        return parentPowerName;
    }

    public void setParentPowerName(String parentPowerName) {
        this.parentPowerName = parentPowerName;
    }

    public Integer getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(Integer powerStatus) {
        this.powerStatus = powerStatus;
    }
}
