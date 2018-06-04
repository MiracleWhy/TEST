package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/18/018.
 */
public class ChildAndSales {
    private Integer id;

    private String childAccountMobile;

    private String childAccountName;

    private String accountPhone;

    private String alias;

    private Date createTime;

    private Date updateTime;

    private String childHeadPic;

    private String saledPrice;

    private int counts;

    private int star;

    private String goodEvaluate;

    private String describes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChildAccountMobile() {
        return childAccountMobile;
    }

    public void setChildAccountMobile(String childAccountMobile) {
        this.childAccountMobile = childAccountMobile;
    }

    public String getChildAccountName() {
        return childAccountName;
    }

    public void setChildAccountName(String childAccountName) {
        this.childAccountName = childAccountName;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getChildHeadPic() {
        return childHeadPic;
    }

    public void setChildHeadPic(String childHeadPic) {
        this.childHeadPic = childHeadPic;
    }

    public String getSaledPrice() {
        return saledPrice;
    }

    public void setSaledPrice(String saledPrice) {
        this.saledPrice = saledPrice;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getGoodEvaluate() {
        return goodEvaluate;
    }

    public void setGoodEvaluate(String goodEvaluate) {
        this.goodEvaluate = goodEvaluate;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
