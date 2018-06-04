package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.model.GleefulReport;

/**
 * Created by SEELE on 2017/7/11.
 */
public class GleefulReportResponse extends GleefulReport {
    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    private int shareCount;

}
