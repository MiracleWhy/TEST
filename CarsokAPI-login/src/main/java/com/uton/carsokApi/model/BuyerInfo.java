package com.uton.carsokApi.model;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class BuyerInfo {
    private String customerId;
    private String tenureCarId;
    private String licensePlate;
    private String vinCode;
    private String sellerId;
    private JSONObject carModel;
    private String salesPrice;
    private String salesMileage;
    private String paymentType;
    private String compulsoryInsuranceDate;
    private String commercialInsuranceDate;
    private String lastMiantenanceDate;
    private String warrantyDueDate;
    private String salesConsultant;
    private String fromApp;
    private SellerInfo sellerInfo;
    private CustomerInfo customerInfo;
    private String engineCode;
    private String buyerPhone;
    private String maintainDate;
    private String yearlyInspectionDate;
    private Integer rescueTime;
    private List<CarService> carService;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTenureCarId() {
        return tenureCarId;
    }

    public void setTenureCarId(String tenureCarId) {
        this.tenureCarId = tenureCarId;
    }

    public List<CarService> getCarService() {
        return carService;
    }

    public void setCarService(List<CarService> carService) {
        this.carService = carService;
    }

    public String getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(String maintainDate) {
        this.maintainDate = maintainDate;
    }

    public String getYearlyInspectionDate() {
        return yearlyInspectionDate;
    }

    public void setYearlyInspectionDate(String yearlyInspectionDate) {
        this.yearlyInspectionDate = yearlyInspectionDate;
    }

    public Integer getRescueTime() {
        return rescueTime;
    }

    public void setRescueTime(Integer rescueTime) {
        this.rescueTime = rescueTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public JSONObject getCarModel() {
        return carModel;
    }

    public void setCarModel(JSONObject carModel) {
        this.carModel = carModel;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getSalesMileage() {
        return salesMileage;
    }

    public void setSalesMileage(String salesMileage) {
        this.salesMileage = salesMileage;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCompulsoryInsuranceDate() {
        return compulsoryInsuranceDate;
    }

    public void setCompulsoryInsuranceDate(String compulsoryInsuranceDate) {
        this.compulsoryInsuranceDate = compulsoryInsuranceDate;
    }

    public String getCommercialInsuranceDate() {
        return commercialInsuranceDate;
    }

    public void setCommercialInsuranceDate(String commercialInsuranceDate) {
        this.commercialInsuranceDate = commercialInsuranceDate;
    }

    public String getLastMiantenanceDate() {
        return lastMiantenanceDate;
    }

    public void setLastMiantenanceDate(String lastMiantenanceDate) {
        this.lastMiantenanceDate = lastMiantenanceDate;
    }

    public String getWarrantyDueDate() {
        return warrantyDueDate;
    }

    public void setWarrantyDueDate(String warrantyDueDate) {
        this.warrantyDueDate = warrantyDueDate;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public String getFromApp() {
        return fromApp;
    }

    public void setFromApp(String fromApp) {
        this.fromApp = fromApp;
    }

    public SellerInfo getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(SellerInfo sellerInfo) {
        this.sellerInfo = sellerInfo;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }
}
