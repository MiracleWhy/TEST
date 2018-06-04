package com.uton.carsokApi.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class CarsokZbTaskSxyWb {
    private Integer id;
    private Integer acquisitioncar_id;
    private Integer infoSource;
    private String carName;
    private String infoName;
    private String infoMobile;
    private String selfName;
    private String selfMobile;
    private String vin;
    private Date create_time;
    private Integer enable;
    private String task_num;
    private Integer taskId;
    private String closeingPrice;

    public String getCloseingPrice() {
        return closeingPrice;
    }

    public void setCloseingPrice(String closeingPrice) {
        this.closeingPrice = closeingPrice;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTask_num() {
        return task_num;
    }

    public void setTask_num(String task_num) {
        this.task_num = task_num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAcquisitioncar_id() {
        return acquisitioncar_id;
    }

    public void setAcquisitioncar_id(Integer acquisitioncar_id) {
        this.acquisitioncar_id = acquisitioncar_id;
    }

    public Integer getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(Integer infoSource) {
        this.infoSource = infoSource;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoMobile() {
        return infoMobile;
    }

    public void setInfoMobile(String infoMobile) {
        this.infoMobile = infoMobile;
    }

    public String getSelfName() {
        return selfName;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }

    public String getSelfMobile() {
        return selfMobile;
    }

    public void setSelfMobile(String selfMobile) {
        this.selfMobile = selfMobile;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
