package com.uton.carsokApi.controller.request;

/**
 * Created by Administrator on 2017/9/27.
 */
public class ModularTransferRequest {
    private Integer beforeChildId;//被转移人id
    private Integer afterChildId;//转移人id

    public Integer getBeforeChildId() {
        return beforeChildId;
    }

    public void setBeforeChildId(Integer beforeChildId) {
        this.beforeChildId = beforeChildId;
    }

    public Integer getAfterChildId() {
        return afterChildId;
    }

    public void setAfterChildId(Integer afterChildId) {
        this.afterChildId = afterChildId;
    }
}
