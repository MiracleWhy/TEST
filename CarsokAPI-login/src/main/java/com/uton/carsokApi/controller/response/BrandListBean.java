package com.uton.carsokApi.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BrandListBean {
    /**
     * carBrandId : 1
     * carBrand : 宝马
     */

    private int carBrandId;
    private String carBrand;
}
