package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.CarFindListRequest;
import com.uton.carsokApi.controller.request.FindCarMsgRequest;
import com.uton.carsokApi.controller.response.CarFindUp;
import com.uton.carsokApi.controller.response.FindCarMsgResponse;
import com.uton.carsokApi.model.CarsokFindCar;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface CarsokFindCarMapper extends BaseMapper<CarsokFindCar> {

    /**
     * @author zhangdi
     * @date 2018/1/19 11:46
     * @Description: 寻车列表
     */
    Page<CarFindUp> getFindCarList(CarFindListRequest c);

    FindCarMsgResponse selectMsg(FindCarMsgRequest f);
}