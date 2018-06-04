package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uton.carsokApi.controller.response.CarTofrontResponse;
import com.uton.carsokApi.model.CarsokCarTofront;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface CarsokCarTofrontMapper extends BaseMapper<CarsokCarTofront> {

    /**
     * 车源置顶记录查询
     * @param accountId
     * @param childId
     * @return
     */
    List<CarTofrontResponse> carTofront(@Param("accountId") Integer accountId, @Param("childId") Integer childId);

}