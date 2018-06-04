package com.uton.carsokApi.controller.response;

import java.io.Serializable;

/**
 * Created by Raytine on 2017/10/18.
 */
public class AllyResponse implements Serializable {

    /**
     * id
     */
    private Integer id;
    /**
     * 车行名称
     */
    private String merchantName;

    /**
     * 盟友编号
     */
    private String friendAccount;

    /**
     * 申请信息
     */
    private String applyMessage;

    /**
     * 好友关系（0： 不是好友（可申请）1：申请中 2：对方接受（已成为好友）3：对方拒绝）
     */
    private Integer relationStatus;

    /**
     * 图片地址
     */
    private String merchantImagePath;

    /**
     * 负责人
     */
    private String contactName;

    /**
     * 地址
     */
    private String merchantAddress;

    /**
     * 在售车辆数量
     */
    private Integer val;

    /**
     * 车商编码
     */
    private String accountCode;

    /**
     * ally表中车商编码
     */
    private String carAccountCode;

    public String getCarAccountCode() {
        return carAccountCode;
    }

    public void setCarAccountCode(String carAccountCode) {
        this.carAccountCode = carAccountCode;
    }

    public String getFriendAccount() {
        return friendAccount;
    }

    public void setFriendAccount(String friendAccount) {
        this.friendAccount = friendAccount;
    }

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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public String getMerchantImagePath() {
        return merchantImagePath;
    }

    public void setMerchantImagePath(String merchantImagePath) {
        this.merchantImagePath = merchantImagePath;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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
}
