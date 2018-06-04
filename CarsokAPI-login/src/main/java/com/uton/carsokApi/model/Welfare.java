package com.uton.carsokApi.model;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
public class Welfare {
    private Integer id;
    private String accountMobile;
    private String referral;
    private String replacementVehicle;
    private String buybackVehicle;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountMobile() {
        return accountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        this.accountMobile = accountMobile;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getReplacementVehicle() {
        return replacementVehicle;
    }

    public void setReplacementVehicle(String replacementVehicle) {
        this.replacementVehicle = replacementVehicle;
    }

    public String getBuybackVehicle() {
        return buybackVehicle;
    }

    public void setBuybackVehicle(String buybackVehicle) {
        this.buybackVehicle = buybackVehicle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
