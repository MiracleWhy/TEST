package com.uton.carsokApi.model;

import java.util.Date;

public class ChildAccount {
    private Integer id;

    private String childAccountMobile;

    private String childAccountName;

    private String accountPhone;
    
    private String alias;

    private Date createTime;

    private Date updateTime;

    private String realName;

    private String caId;

    private String ccaId;

    private String childHeadPic;

    private int star;

    private String goodEvaluate;

    private String describes;

    private int counts;

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

    public String getChildHeadPic() {
        return childHeadPic;
    }

    public void setChildHeadPic(String childHeadPic) {
        this.childHeadPic = childHeadPic;
    }

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
        this.childAccountMobile = childAccountMobile == null ? null : childAccountMobile.trim();
    }

    public String getChildAccountName() {
        return childAccountName;
    }

    public void setChildAccountName(String childAccountName) {
        this.childAccountName = childAccountName == null ? null : childAccountName.trim();
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone == null ? null : accountPhone.trim();
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCaId() {
        return caId;
    }

    public void setCaId(String caId) {
        this.caId = caId;
    }

    public String getCcaId() {
        return ccaId;
    }

    public void setCcaId(String ccaId) {
        this.ccaId = ccaId;
    }
}