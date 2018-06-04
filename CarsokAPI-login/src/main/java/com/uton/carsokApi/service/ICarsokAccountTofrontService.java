package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.MerchantListRequest;
import com.uton.carsokApi.controller.response.MerchantListResponse;
import com.uton.carsokApi.model.CarsokAccountTofront;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface ICarsokAccountTofrontService extends IService<CarsokAccountTofront> {

    Page<MerchantListResponse> getMmerchantList (MerchantListRequest m);

    /**
     * 查询置顶记录
     * @param accountId
     * @param childId
     * @return
     */
    Map<String,Object> getTofrontHistoryData(Integer accountId, Integer childId,Integer pageNum,Integer pageSize);
	
}
