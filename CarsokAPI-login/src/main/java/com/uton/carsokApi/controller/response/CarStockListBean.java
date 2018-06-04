package com.uton.carsokApi.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class CarStockListBean implements Serializable{
    /**
     * id : 1
     * carBrand : 宝马
     * carBrandId : 1
     * carSeries : 宝马5系
     * picPath : http://pic.utonw.com/FqyZRFqFv_6mywAOXoSpnjUZ7ZX0
     */

    private int id;
    private String carBrand;
    private int carBrandId;
    private String carSeries;
    private String picPath;
}
