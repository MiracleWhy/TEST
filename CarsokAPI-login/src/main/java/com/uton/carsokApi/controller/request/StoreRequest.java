package com.uton.carsokApi.controller.request;

/**
 * Created by Administrator on 2017/8/28.
 * 门店请求实体类
 */
public class StoreRequest {
    private String id;//门店id
    private String times;//时间标识
    private String xxly;//信息来源
    private String khjb;//客户级别
    private String dfzt;//到访状态
    private String gmys;//购买预算
    private String mobile;//当前登陆人电话
    private String month;//月份标识
    private String pc;//当前页
    private String select;//搜索关键字
    private String custP;//客户电话
    private String custN;//客户姓名
    private String custI;//门店id
    private String custFlow;//跟踪信息
    private String customerStatus;//客户状态
    private String remind;//提醒时间
    private String comeIf;//是否到店

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getXxly() {
        return xxly;
    }

    public void setXxly(String xxly) {
        this.xxly = xxly;
    }

    public String getKhjb() {
        return khjb;
    }

    public void setKhjb(String khjb) {
        this.khjb = khjb;
    }

    public String getDfzt() {
        return dfzt;
    }

    public void setDfzt(String dfzt) {
        this.dfzt = dfzt;
    }

    public String getGmys() {
        return gmys;
    }

    public void setGmys(String gmys) {
        this.gmys = gmys;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getCustP() {
        return custP;
    }

    public void setCustP(String custP) {
        this.custP = custP;
    }

    public String getCustN() {
        return custN;
    }

    public void setCustN(String custN) {
        this.custN = custN;
    }

    public String getCustI() {
        return custI;
    }

    public void setCustI(String custI) {
        this.custI = custI;
    }

    public String getCustFlow() {
        return custFlow;
    }

    public void setCustFlow(String custFlow) {
        this.custFlow = custFlow;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getComeIf() {
        return comeIf;
    }

    public void setComeIf(String comeIf) {
        this.comeIf = comeIf;
    }
}
