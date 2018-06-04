package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.controller.request.CarCollectRequest;
import com.uton.carsokApi.model.CarsokAccountCollect;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface ICarsokAccountCollectService extends IService<CarsokAccountCollect> {
    /**
     * 获取车行收藏列表
     * @param carCollectRequest
     * @return
     */
    Map getAccountCollectList(CarCollectRequest carCollectRequest);

    /**
     * 添加收藏车行
     * @param carsokAccountCollect
     * @return
     */
    int addAccountCollect(CarsokAccountCollect carsokAccountCollect);

    /**
     * 取消车行收藏
     * @param id
     * @return
     */
    Boolean delAccountCollect(Integer id);

    /**
     * 查询车行是否被收藏
     * @param accountId
     * @param childId
     * @param collectAccountId
     * @return
     */
    Integer isCollectAccount(Integer accountId,Integer childId,Integer collectAccountId);
	
}
