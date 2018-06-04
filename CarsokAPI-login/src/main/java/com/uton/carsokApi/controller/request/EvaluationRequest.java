package com.uton.carsokApi.controller.request;

/**
 * Created by Administrator on 2017/10/11.
 */
public class EvaluationRequest {
    private String accountId;//车辆id
    private String vehicleModel;//客户车型
    private String name;//客户姓名
    private String mobile;//客户手机号
    private String isCar;//是否是车辆详情页的卖车评估

    public String getIsCar() {
        return isCar;
    }

    public void setIsCar(String isCar) {
        this.isCar = isCar;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
