package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.controller.request.CarCollectRequest;
import com.uton.carsokApi.controller.response.ShareAccountInfo;
import com.uton.carsokApi.model.CarsokCarCollect;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface ICarsokCarCollectService extends IService<CarsokCarCollect> {
    /**
     * 获取车源收藏列表
     * @param carCollectRequest
     * @return
     */
    Map getCarCollectList(CarCollectRequest carCollectRequest);

    /**
     * 添加车源收藏
     * @param carsokCarCollect
     * @return
     */
    int addCarCollect(CarsokCarCollect carsokCarCollect);

    /**
     * 取消车源收藏
     * @param id
     * @return
     */
    Boolean delCarCollect(Integer id);

    /**
     * 判断是否收藏
     * @param accountId
     * @param childId
     * @param collectCarId
     * @return
     */
    Integer isCollectCar(Integer accountId,Integer childId,Integer collectCarId);

    /**
     * 查询分享车行信息
     * @param accountId
     * @return
     */
    ShareAccountInfo getAcountInfo(Integer accountId);
	
}
