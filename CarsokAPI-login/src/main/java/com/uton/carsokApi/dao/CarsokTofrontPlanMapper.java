package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uton.carsokApi.controller.response.TofrontPlanResponse;
import com.uton.carsokApi.model.CarsokTofrontPlan;
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
public interface CarsokTofrontPlanMapper extends BaseMapper<CarsokTofrontPlan> {

    /**
     * 车行置顶套餐查询
     * @return
     */
    List<TofrontPlanResponse> getAccountTofrontPlan();

    /**
     * 车源置顶套餐查询
     * @return
     */
    List<TofrontPlanResponse> getCarTofrontPlan();

    /**
     * 查询子账号权限（0：无权限 1：有权限）
     * @param childId
     * @param powerName
     * @return
     */
    Integer getIsShow(@Param("childId") Integer childId,@Param("powerName") String powerName);

}