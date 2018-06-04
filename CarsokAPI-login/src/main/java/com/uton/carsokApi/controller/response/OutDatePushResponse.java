package com.uton.carsokApi.controller.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class OutDatePushResponse {

    private String remindTitle;
//    private String remindInfo1;
//    private String remindInfo2;
    private List<Map> remindInfoArray;
    private Date createTime;

    public String getRemindTitle() {
        return remindTitle;
    }

    public void setRemindTitle(String remindTitle) {
        this.remindTitle = remindTitle;
    }

//    public String getRemindInfo1() {
//        return remindInfo1;
//    }
//
//    public void setRemindInfo1(String remindInfo1) {
//        this.remindInfo1 = remindInfo1;
//    }
//
//    public String getRemindInfo2() {
//        return remindInfo2;
//    }
//
//    public void setRemindInfo2(String remindInfo2) {
//        this.remindInfo2 = remindInfo2;
//    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Map> getRemindInfoArray() {
        return remindInfoArray;
    }

    public void setRemindInfoArray(List<Map> remindInfoArray) {
        this.remindInfoArray = remindInfoArray;
    }
}
