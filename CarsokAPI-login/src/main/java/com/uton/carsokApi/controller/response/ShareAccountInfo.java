package com.uton.carsokApi.controller.response;

import lombok.Data;

/**
 * Created by Raytine on 2018/1/3.
 */
@Data
public class ShareAccountInfo {
    /**
     * 车行名称
     */
    private String merchantName;

    /**
     * 车行首图
     */
    private String merchantImagePath;
}
