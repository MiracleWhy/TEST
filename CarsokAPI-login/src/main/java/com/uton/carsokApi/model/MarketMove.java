package com.uton.carsokApi.model;

/**
 * Created by Raytine on 2017/9/7.
 */
public class MarketMove {

    private String carname;//车行名称
    private String leadingofficial;//负责人
    private String address;//地址
    private String name;//子账户姓名
    private String id;
    private String mobile;
    private String carGM;

    public String getCarGM() {
        return carGM;
    }

    public void setCarGM(String carGM) {
        this.carGM = carGM;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getLeadingofficial() {
        return leadingofficial;
    }

    public void setLeadingofficial(String leadingofficial) {
        this.leadingofficial = leadingofficial;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
