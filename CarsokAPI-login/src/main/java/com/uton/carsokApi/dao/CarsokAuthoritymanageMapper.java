package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uton.carsokApi.model.CarsokAuthoritymanage;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
public interface CarsokAuthoritymanageMapper extends BaseMapper<CarsokAuthoritymanage> {

    int insertCarsokAuthoritymanage(CarsokAuthoritymanage carsokAuthoritymanage);
}