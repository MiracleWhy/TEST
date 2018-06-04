package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uton.carsokApi.controller.response.TofrontPlanResponse;
import com.uton.carsokApi.dao.CarsokTofrontPlanMapper;
import com.uton.carsokApi.model.CarsokTofrontPlan;
import com.uton.carsokApi.service.ICarsokTofrontPlanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Service
public class CarsokTofrontPlanServiceImpl extends ServiceImpl<CarsokTofrontPlanMapper, CarsokTofrontPlan> implements ICarsokTofrontPlanService {


    private final static Logger logger = Logger.getLogger(CarsokTofrontPlanServiceImpl.class);

    @Autowired
    CarsokTofrontPlanMapper carsokTofrontPlanMapper;

    /**
     * 车行置顶套餐查询
     * @return
     */
    @Override
    public List<TofrontPlanResponse> getAccountTofrontPlan() {
        return carsokTofrontPlanMapper.getAccountTofrontPlan();
    }

    /**
     * 车源置顶套餐查询
     * @return
     */
    @Override
    public List<TofrontPlanResponse> getCarTofrontPlan() {
        return carsokTofrontPlanMapper.getCarTofrontPlan();
    }

    /**
     * 查询子账号权限（0：无权限 1：有权限）
     * @param childId
     * @param powerName
     * @return
     */
    @Override
    public Integer getIsShow(Integer childId, String powerName) {
        return carsokTofrontPlanMapper.getIsShow(childId,powerName);
    }
}
