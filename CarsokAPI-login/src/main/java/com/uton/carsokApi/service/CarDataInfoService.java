package com.uton.carsokApi.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.pagehelper.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.BZYCarDataRequest;
import com.uton.carsokApi.controller.request.CarDataSearchRequest;
import com.uton.carsokApi.controller.request.ThirdDataSellCountRequest;
import com.uton.carsokApi.controller.response.ThirdDataSellCountResponse;
import com.uton.carsokApi.model.CarDataImage;
import com.uton.carsokApi.model.CarDataInfo;

public interface CarDataInfoService {
	/**
	 * 创建车辆信息
	 * 
	 * @param carDataInfo
	 * @return
	 */
	public BaseResult createCarDataInfo(CarDataInfo carDataInfo);

	/**
	 * 创建车辆和图片信息
	 * 
	 * @param request
	 * @return
	 */
	public BaseResult createCarDataInfoAndImage(BZYCarDataRequest request);

	/**
	 * 创建车辆图片信息
	 */
	public BaseResult createCarDataImages(List<CarDataImage> carDataImages, String uuid);

	/**
	 * 第三方认证
	 */
	public BaseResult validatePartner(String partner, String token);
	/**
	 * 
	 */
	public CarDataInfo  queryById(String id);

	public ThirdDataSellCountResponse querySellCount(ThirdDataSellCountRequest countRequest);
	
	public Page<CarDataInfo> queryByCondition(CarDataSearchRequest carDataRequest);

	public BigDecimal getPrice(CarDataInfo carDataInfo);

	public CarDataImage queryCarImage(String uuid);

}
