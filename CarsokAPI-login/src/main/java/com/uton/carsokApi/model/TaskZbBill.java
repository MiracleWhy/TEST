package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/5.
 */
public class TaskZbBill {
    private Integer id;
    private Integer tid;
    private String billName;
    private String billPic;
    private Integer againTimes;
    private Date againTime;

    public Date getAgainTime() {
        return againTime;
    }

    public void setAgainTime(Date againTime) {
        this.againTime = againTime;
    }

    public Integer getAgainTimes() {
        return againTimes;
    }

    public void setAgainTimes(Integer againTimes) {
        this.againTimes = againTimes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBillPic() {
        return billPic;
    }

    public void setBillPic(String billPic) {
        this.billPic = billPic;
    }
}
