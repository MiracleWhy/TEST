package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.model.DailyReportZan;

import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
public class DailyReportZanResponse {
    private String type;
    private String title;
    private List<DailyReportZan> zanList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DailyReportZan> getZanList() {
        return zanList;
    }

    public void setZanList(List<DailyReportZan> zanList) {
        this.zanList = zanList;
    }
}
