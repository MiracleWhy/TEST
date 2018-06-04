package com.uton.carsokApi.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18/018.
 */
public class SelectNextFull {
    private String followupType;
    private List<AddNextVisit> list;

    public String getFollowupType() {
        return followupType;
    }

    public void setFollowupType(String followupType) {
        this.followupType = followupType;
    }

    public List<AddNextVisit> getList() {
        return list;
    }

    public void setList(List<AddNextVisit> list) {
        this.list = list;
    }
}
