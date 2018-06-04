package com.uton.carsokApi.controller.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by SEELE on 2017/11/11.
 */
public class FollowResponse implements Serializable{

    private Integer followTotalCount; //跟进数量(第一行)
    private Integer followSum;//跟进合计 (第一行)
    private Double chainSum;//环比(第一行)
    private String upOrDownSum;//上下箭头(第一行)
    private List<FollowVoResponse> rows;//List  |行数据（数组）


    public Integer getFollowTotalCount() {
        return followTotalCount;
    }

    public void setFollowTotalCount(Integer followTotalCount) {
        this.followTotalCount = followTotalCount;
    }

    public Integer getFollowSum() {
        return followSum;
    }

    public void setFollowSum(Integer followSum) {
        this.followSum = followSum;
    }

    public Double getChainSum() {
        return chainSum;
    }

    public void setChainSum(Double chainSum) {
        this.chainSum = chainSum;
    }

    public String getUpOrDownSum() {
        return upOrDownSum;
    }

    public void setUpOrDownSum(String upOrDownSum) {
        this.upOrDownSum = upOrDownSum;
    }

    public List<FollowVoResponse> getRows() {
        return rows;
    }

    public void setRows(List<FollowVoResponse> rows) {
        this.rows = rows;
    }
}
