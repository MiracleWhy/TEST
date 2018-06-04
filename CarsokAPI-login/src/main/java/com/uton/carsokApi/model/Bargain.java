package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/11.
 */
public class Bargain {
    private Integer id;
    private Integer productId;//车辆id
    private String intentionalPrice;//意向价格
    private String name;//姓名
    private String mobile;//联系电话
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getIntentionalPrice() {
        return intentionalPrice;
    }

    public void setIntentionalPrice(String intentionalPrice) {
        this.intentionalPrice = intentionalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
