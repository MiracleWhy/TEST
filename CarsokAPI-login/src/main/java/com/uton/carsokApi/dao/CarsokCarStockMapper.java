package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.CarsokCarStock;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author csw
 * @since 2017-11-08
 */
public interface CarsokCarStockMapper extends BaseMapper<CarsokCarStock> {


    List<CarsokCarStock> selectCarsokCarStockList(CarsokCarStock carsokCarStock);

}