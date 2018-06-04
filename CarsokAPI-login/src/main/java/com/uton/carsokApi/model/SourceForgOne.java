package com.uton.carsokApi.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/11/13/013.
 */
public class SourceForgOne {
    private  String source;
    private Integer people;



    private Double chain;

    public Double getChain() {
        return chain;
    }

    public void setChain(Double chain) {
        this.chain = chain;
    }

    private Double rate;
    private String UpOrDown;

    public String getUpOrDown() {

        return UpOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        UpOrDown = upOrDown;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPeople(Integer n) {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public Integer getPeople() {
        return people;
    }

    public Double getRate() {

        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
