package com.uton.carsokApi.controller.response;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/11/11.
 */
public class FollowVoResponse implements Serializable{
    private Integer followOneCount;//个人跟进数量
    private Integer followOneTotal;//个人跟进总计
    private Double chain;//个人环比
    private String consultant;//销售顾问
    private String upOrDown;//上下箭头
    private Integer ranking;//排名

    public Integer getFollowOneCount() {
        return followOneCount;
    }

    public void setFollowOneCount(Integer followOneCount) {
        this.followOneCount = followOneCount;
    }

    public Integer getFollowOneTotal() {
        return followOneTotal;
    }

    public void setFollowOneTotal(Integer followOneTotal) {
        this.followOneTotal = followOneTotal;
    }

    public Double getChain() {
        return chain;
    }

    public void setChain(Double chain) {
        this.chain = chain;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        this.upOrDown = upOrDown;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
