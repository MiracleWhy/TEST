package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raytine on 2017/11/9.
 */
@Data
public class CarsokProductResponse implements Serializable {

    private List<InventoryResponse> carStockList;

    private long listCount;

    private int onSaleCount;

    private int saledCount;
}
