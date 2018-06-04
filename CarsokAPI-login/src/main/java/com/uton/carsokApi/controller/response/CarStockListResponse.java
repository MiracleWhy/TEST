package com.uton.carsokApi.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class CarStockListResponse implements Serializable {


    /**
     * listCount : 22
     * carStockList : [{"id":1,"carBrand":"宝马","carBrandId":1,"carSeries":"宝马5系","picPath":"http://pic.utonw.com/FqyZRFqFv_6mywAOXoSpnjUZ7ZX0"},{"id":2,"carBrand":"奥迪","carBrandId":2,"carSeries":"奥迪A6L","picPath":"http://pic.utonw.com/FqyZRFqFv_6mywAOXoSpnjUZ7ZX0"},{"id":3,"carBrand":"宝马","carBrandId":3,"carSeries":"宝马3系","picPath":"http://pic.utonw.com/FqyZRFqFv_6mywAOXoSpnjUZ7ZX0"}]
     */


    private long listCount;
    private List<CarStockListBean> carStockList;

    public CarStockListResponse(long listCount, List<CarStockListBean> carStockList) {
        this.listCount = listCount;
        this.carStockList = carStockList;
    }
}
