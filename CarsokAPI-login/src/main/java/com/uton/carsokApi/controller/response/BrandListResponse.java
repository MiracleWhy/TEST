package com.uton.carsokApi.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class BrandListResponse implements Serializable {


    private List<BrandListBean> brandList;

    public BrandListResponse(List<BrandListBean> brandList) {
        this.brandList = brandList;
    }
}
