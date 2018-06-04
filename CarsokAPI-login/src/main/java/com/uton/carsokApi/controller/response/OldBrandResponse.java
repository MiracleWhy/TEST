package com.uton.carsokApi.controller.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Raytine on 2017/11/8.
 */
@Data
public class OldBrandResponse implements Serializable {

    /**
     * 品牌
     */
    private String carBrand;

    /**
     * 品牌id
     */
    private int carBrandId;
}
