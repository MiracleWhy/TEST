package com.uton.carsokApi.model;

import java.util.Date;

import com.uton.carsokApi.constants.enums.PosApplyStatusEnum;

public class PosApply {
    private Integer id;

    private String accountId;

    private String applyAccountInfo;

    private Date gmtCreate;

    private Date gmtModify;

    private Date gmtEnd;

    private PosApplyStatusEnum applyStatus;

    private String applyInfo;

    private String reviewInfo;

    private String bizId;

    private String memo;

    private Boolean enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getApplyAccountInfo() {
        return applyAccountInfo;
    }

    public void setApplyAccountInfo(String applyAccountInfo) {
        this.applyAccountInfo = applyAccountInfo == null ? null : applyAccountInfo.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Date getGmtEnd() {
        return gmtEnd;
    }

    public void setGmtEnd(Date gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    public PosApplyStatusEnum getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(PosApplyStatusEnum applyStatus) {
        this.applyStatus=applyStatus ;
    }

    public String getApplyInfo() {
        return applyInfo;
    }

    public void setApplyInfo(String applyInfo) {
        this.applyInfo = applyInfo == null ? null : applyInfo.trim();
    }

    public String getReviewInfo() {
        return reviewInfo;
    }

    public void setReviewInfo(String reviewInfo) {
        this.reviewInfo = reviewInfo == null ? null : reviewInfo.trim();
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId == null ? null : bizId.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}