package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.CarsokPruductOldcar;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 商品辅表（二手车） Mapper 接口
 * </p>
 *
 * @author csw
 * @since 2017-11-10
 */
public interface CarsokPruductOldcarMapper extends BaseMapper<CarsokPruductOldcar> {

    int getTodayViewCount();
    int getTotleViewCount();

}