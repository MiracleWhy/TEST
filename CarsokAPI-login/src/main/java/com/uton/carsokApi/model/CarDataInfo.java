package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.uton.carsokApi.util.DateUtil;

public class CarDataInfo {
	private Integer id;
	@NotEmpty
	private String phone;

	private String province;

	private boolean isExchange;

	private boolean isDealer;

	private String dealerName;

	private String dealerPhone;

	private String dealerProvince;

	private String dealerAddress;

	private String carBrand;

	private String carSeries;

	private String carImage;

	private String carModel;

	private String carColor;

	private BigDecimal supervisedPrice;

	private BigDecimal sellPrice;

	private Date gmtCreate;

	private Date gmtModify;

	private String productionStatus;

	private String uuid;

	private String address;

	private boolean enable;

	private String mileage;

	private Date cardDate;

	private String carDescription;

	private String otherDescription;

	private String otherContactWay;
	
	private String provider;
	
	private String thirdId;
	
	private List<CarDataImage> images;
	
	private boolean isBuy;
	
	private BigDecimal price;
	
	
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isBuy() {
		return isBuy;
	}

	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}

	public List<CarDataImage> getImages() {
		return images;
	}

	public void setImages(List<CarDataImage> images) {
		this.images = images;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getCardDate() {
		return DateUtil.DateToString(cardDate, "yyyy-MM-dd");
	}

	public void setCardDate(Date cardDate) {
		this.cardDate = cardDate;
	}

	public String getCarDescription() {
		return carDescription;
	}

	public void setCarDescription(String carDescription) {
		this.carDescription = carDescription;
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	public String getOtherContactWay() {
		return otherContactWay;
	}

	public void setOtherContactWay(String otherContactWay) {
		this.otherContactWay = otherContactWay;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName == null ? null : dealerName.trim();
	}

	public String getDealerPhone() {
		return dealerPhone;
	}

	public void setDealerPhone(String dealerPhone) {
		this.dealerPhone = dealerPhone == null ? null : dealerPhone.trim();
	}

	public String getDealerProvince() {
		return dealerProvince;
	}

	public void setDealerProvince(String dealerProvince) {
		this.dealerProvince = dealerProvince == null ? null : dealerProvince.trim();
	}

	public String getDealerAddress() {
		return dealerAddress;
	}

	public void setDealerAddress(String dealerAddress) {
		this.dealerAddress = dealerAddress == null ? null : dealerAddress.trim();
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand == null ? null : carBrand.trim();
	}

	public String getCarSeries() {
		return carSeries;
	}

	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries == null ? null : carSeries.trim();
	}

	public String getCarImage() {
		return carImage;
	}

	public void setCarImage(String carImage) {
		this.carImage = carImage == null ? null : carImage.trim();
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel == null ? null : carModel.trim();
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor == null ? null : carColor.trim();
	}


	public BigDecimal getSupervisedPrice() {
		return supervisedPrice;
	}

	public void setSupervisedPrice(BigDecimal supervisedPrice) {
		this.supervisedPrice = supervisedPrice;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getProductionStatus() {
		return productionStatus;
	}

	public void setProductionStatus(String productionStatus) {
		this.productionStatus = productionStatus == null ? null : productionStatus.trim();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid == null ? null : uuid.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public boolean isExchange() {
		return isExchange;
	}

	public void setExchange(boolean isExchange) {
		this.isExchange = isExchange;
	}

	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}