package com.uton.carsokApi.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class CarService {
    private String autoServiceType;
    private String autoServiceDate;
    private String autoServiceMoney;
    private String autoServiceContent;
    private String autoServiceUrl;

    public String getAutoServiceDate() {
        return autoServiceDate;
    }

    public void setAutoServiceDate(String autoServiceDate) {
        this.autoServiceDate = autoServiceDate;
    }

    public String getAutoServiceType() {
        return autoServiceType;
    }

    public void setAutoServiceType(String autoServiceType) {
        this.autoServiceType = autoServiceType;
    }

    public String getAutoServiceMoney() {
        return autoServiceMoney;
    }

    public void setAutoServiceMoney(String autoServiceMoney) {
        this.autoServiceMoney = autoServiceMoney;
    }

    public String getAutoServiceContent() {
        return autoServiceContent;
    }

    public void setAutoServiceContent(String autoServiceContent) {
        this.autoServiceContent = autoServiceContent;
    }

    public String getAutoServiceUrl() {
        return autoServiceUrl;
    }

    public void setAutoServiceUrl(String autoServiceUrl) {
        this.autoServiceUrl = autoServiceUrl;
    }
}
