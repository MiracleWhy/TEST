package com.uton.carsokApi.controller.request;

import java.util.Date;

/**
 * Created by Raytine on 2017/10/18.
 */
public class AllyRequest {

    /**
     * id
     */
    private Integer id;

    /**
     * 商家编号
     */
    private String accountCode;

    /**
     * 盟友编号
     */
    private String friendAccount;

    /**
     * 申请消息
     */
    private String applyMessage;

    /**
     * 好友关系（0： 不是好友（可申请）1：申请中 2：对方接受（已成为好友）3：对方拒绝）
     */
    private Integer relationStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getFriendAccount() {
        return friendAccount;
    }

    public void setFriendAccount(String friendAccount) {
        this.friendAccount = friendAccount;
    }

    public String getApplyMessage() {
        return applyMessage;
    }

    public void setApplyMessage(String applyMessage) {
        this.applyMessage = applyMessage;
    }

    public Integer getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(Integer relationStatus) {
        this.relationStatus = relationStatus;
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
}
