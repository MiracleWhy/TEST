package com.uton.carsokApi.controller.request;

/**
 * Created by Administrator on 2017/6/22.
 */
public class ProductReserveRequest {
    private Integer id;
    private Integer reserveIf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReserveIf() {
        return reserveIf;
    }

    public void setReserveIf(Integer reserveIf) {
        this.reserveIf = reserveIf;
    }
}
