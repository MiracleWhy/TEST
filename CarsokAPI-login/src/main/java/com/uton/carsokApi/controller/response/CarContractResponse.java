package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.controller.request.Page;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */
public class CarContractResponse extends Page {
    private String car;//车辆信息
    private String carId;
    private List<ContractResponse> contractResponseList;//合同列表

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getBrand() {
        return car;
    }

    public void setBrand(String car) {
        this.car = car;
    }

    public List<ContractResponse> getContractResponseList() {
        return contractResponseList;
    }

    public void setContractResponseList(List<ContractResponse> contractResponseList) {
        this.contractResponseList = contractResponseList;
    }
}
