package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.CarFindListRequest;
import com.uton.carsokApi.controller.request.FindCarMsgRequest;
import com.uton.carsokApi.controller.request.PublishFindCarRequest;
import com.uton.carsokApi.controller.response.CarFindUp;
import com.uton.carsokApi.controller.response.FindCarMsgResponse;
import com.uton.carsokApi.model.CarsokFindCar;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface ICarsokFindCarService extends IService<CarsokFindCar> {

    Page<CarFindUp> getCarFindList(CarFindListRequest carFindListRequest);

    boolean publishFindCar (PublishFindCarRequest p);

    FindCarMsgResponse selectMsg(FindCarMsgRequest f);
	
}
