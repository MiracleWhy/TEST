package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.controller.request.AccountTofrontPrepayRequest;
import com.uton.carsokApi.model.CarsokAccountTofrontOrder;

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
public interface ICarsokAccountTofrontOrderService extends IService<CarsokAccountTofrontOrder> {

    SortedMap<String,String>  prepay(AccountTofrontPrepayRequest a);
    boolean notify(Map map);


}
