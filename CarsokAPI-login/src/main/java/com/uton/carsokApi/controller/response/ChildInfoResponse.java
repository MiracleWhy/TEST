package com.uton.carsokApi.controller.response;

/**
 * Created by Administrator on 2017/8/14.
 */
public class ChildInfoResponse {
    private Integer childId;
    private String childAccountName;
    private String childAccountMobile;
    private String merchantName;
    private String province;
    private String city;
    private String merchantAddress;
    private String merchantDescr;
    private String roleName;
    private String childHeadPic;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getChildAccountName() {
        return childAccountName;
    }

    public void setChildAccountName(String childAccountName) {
        this.childAccountName = childAccountName;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getChildAccountMobile() {
        return childAccountMobile;
    }

    public void setChildAccountMobile(String childAccountMobile) {
        this.childAccountMobile = childAccountMobile;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantDescr() {
        return merchantDescr;
    }

    public void setMerchantDescr(String merchantDescr) {
        this.merchantDescr = merchantDescr;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getChildHeadPic() {
        return childHeadPic;
    }

    public void setChildHeadPic(String childHeadPic) {
        this.childHeadPic = childHeadPic;
    }
}
