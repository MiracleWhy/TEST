package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.controller.request.CarTofrontPrepayRequest;
import com.uton.carsokApi.model.CarsokCarTofrontOrder;

import java.util.Map;
import java.util.SortedMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface ICarsokCarTofrontOrderService extends IService<CarsokCarTofrontOrder> {

    SortedMap<String,String> prepay(CarTofrontPrepayRequest c);

    boolean notify(Map map);
	
}
