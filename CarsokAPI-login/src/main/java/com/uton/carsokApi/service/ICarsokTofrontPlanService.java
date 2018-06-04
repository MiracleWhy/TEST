package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.controller.response.TofrontPlanResponse;
import com.uton.carsokApi.model.CarsokTofrontPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface ICarsokTofrontPlanService extends IService<CarsokTofrontPlan> {

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
    Integer getIsShow(Integer childId,String powerName);
	
}
