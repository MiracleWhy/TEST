package com.uton.carsokApi.model;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */
public class PermissionManagement {
    private Integer id;
    private String powerName;
    private String powerValue;
    private Integer parentId;
    private Integer powerStatus;
    private Integer powerIf;
    private List<PermissionManagement> childPowerList;
    private String iconName;
    private String type;
    private String showProfessionallcon;

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShowProfessionallcon() {
        return showProfessionallcon;
    }

    public void setShowProfessionallcon(String showProfessionallcon) {
        this.showProfessionallcon = showProfessionallcon;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(Integer powerStatus) {
        this.powerStatus = powerStatus;
    }

    public List<PermissionManagement> getChildPowerList() {
        return childPowerList;
    }

    public void setChildPowerList(List<PermissionManagement> childPowerList) {
        this.childPowerList = childPowerList;
    }

    public Integer getPowerIf() {
        return powerIf;
    }

    public void setPowerIf(Integer powerIf) {
        this.powerIf = powerIf;
    }
}
