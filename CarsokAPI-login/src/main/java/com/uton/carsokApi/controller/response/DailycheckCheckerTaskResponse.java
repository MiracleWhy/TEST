package com.uton.carsokApi.controller.response;

/**
 * Created by SEELE on 2017/7/4.
 */
public class DailycheckCheckerTaskResponse {
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    private String accountId;
    private String account;
}
