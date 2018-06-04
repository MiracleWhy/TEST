package com.uton.carsokApi.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class CarStockInfoResponse implements Serializable {


    /**
     * id : 1
     * carBrand : 宝马
     * carSeries : 宝马5系
     * picList : [{"id":1,"picPath":"http://pic.utonw.com/FqyZRFqFv_6mywAOXoSpnjUZ7ZX0","type":1}]
     */

    private int id;
    private String carBrand;
    private Integer carBrandId;
    private String carSeries;
    private List<PicListBean> picList;

    public CarStockInfoResponse(int id, String carBrand, Integer carBrandId, String carSeries, List<PicListBean> picList) {
        this.id = id;
        this.carBrand = carBrand;
        this.carBrandId = carBrandId;
        this.carSeries = carSeries;
        this.picList = picList;
    }
}
