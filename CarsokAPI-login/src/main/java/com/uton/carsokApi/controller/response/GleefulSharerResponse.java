package com.uton.carsokApi.controller.response;

/**
 * Created by SEELE on 2017/7/14.
 */
public class GleefulSharerResponse {
    public String getSharer() {
        return sharer;
    }

    public void setSharer(String sharer) {
        this.sharer = sharer;
    }

    public String getSharerName() {
        return sharerName;
    }

    public void setSharerName(String sharerName) {
        this.sharerName = sharerName;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    private String sharer;
    private String sharerName;
    private String isCheck;
}
