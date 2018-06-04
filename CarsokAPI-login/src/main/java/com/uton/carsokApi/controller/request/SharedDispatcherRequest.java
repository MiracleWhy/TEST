package com.uton.carsokApi.controller.request;

import java.util.List;

/**
 * Created by SEELE on 2017/7/17.
 */
public class SharedDispatcherRequest {
    public List<String> getShares() {
        return shares;
    }

    public void setShares(List<String> shares) {
        this.shares = shares;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private List<String> shares;
    private String type;

}
