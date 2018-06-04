package com.uton.carsokApi.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raytine on 2017/9/7.
 */
public class DatumManageResponse implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String productName;

    /**
     *vin码
     */
    private String vin;

    /**
     * 销售状态 0 在售 1售出
     */
    private String saleStatus;

    /**
     * 上架时间
     */
    private String onShelfTime;

    /**
     * 图片
     */
    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getOnShelfTime() {
        return onShelfTime;
    }

    public void setOnShelfTime(String onShelfTime) {
        this.onShelfTime = onShelfTime;
    }
}
