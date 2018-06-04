package com.uton.carsokApi.controller.response;

/**
 * Created by Raytine on 2017/9/11.
 */
public class ZbBillPicResponse {
    /**
     * id
     */
    private Integer id;

    /**
     * 单据名称
     */
    private String billName;

    /**
     * 图片地址
     */
    private String billPic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBillPic() {
        return billPic;
    }

    public void setBillPic(String billPic) {
        this.billPic = billPic;
    }
}
