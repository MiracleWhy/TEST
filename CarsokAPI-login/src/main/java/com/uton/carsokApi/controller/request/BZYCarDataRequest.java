package com.uton.carsokApi.controller.request;

import java.util.List;

import com.uton.carsokApi.model.CarDataImage;
import com.uton.carsokApi.model.CarDataInfo;

public class BZYCarDataRequest {
	private CarDataInfo carInfo;
	private List<CarDataImage> carImages;

	public CarDataInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CarDataInfo carInfo) {
		this.carInfo = carInfo;
	}

	public List<CarDataImage> getCarImages() {
		return carImages;
	}

	public void setCarImages(List<CarDataImage> carImages) {
		this.carImages = carImages;
	}

}
