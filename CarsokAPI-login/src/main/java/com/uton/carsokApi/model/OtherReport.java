package com.uton.carsokApi.model;

import java.sql.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */
public class OtherReport {
    private Date searchDate;
    private Object peopleYNum;
    private Object peopleNNum;
    private String gleefulContent;
    private String shareId;
    private java.util.Date shareDate;
    private String type;
    private List<OtherShareContent> otherList;
    private String picList;

    public List<OtherShareContent> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<OtherShareContent> otherList) {
        this.otherList = otherList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public java.util.Date getShareDate() {
        return shareDate;
    }

    public void setShareDate(java.util.Date shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public Object getPeopleYNum() {
        return peopleYNum;
    }

    public void setPeopleYNum(Object peopleYNum) {
        this.peopleYNum = peopleYNum;
    }

    public Object getPeopleNNum() {
        return peopleNNum;
    }

    public void setPeopleNNum(Object peopleNNum) {
        this.peopleNNum = peopleNNum;
    }

    public String getGleefulContent() {
        return gleefulContent;
    }

    public void setGleefulContent(String gleefulContent) {
        this.gleefulContent = gleefulContent;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }
}
