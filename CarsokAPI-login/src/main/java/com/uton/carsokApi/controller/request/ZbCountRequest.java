package com.uton.carsokApi.controller.request;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
public class ZbCountRequest {
    private String accountMobile;
    private List<String> roleList;

    public String getAccountMobile() {
        return accountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        this.accountMobile = accountMobile;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }
}
