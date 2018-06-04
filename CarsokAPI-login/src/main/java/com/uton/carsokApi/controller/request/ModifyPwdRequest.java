package com.uton.carsokApi.controller.request;

/**
 * Created by SEELE on 2017/10/17.
 */
public class ModifyPwdRequest {
    private String password;
    private String newPassword;
    private String account;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
