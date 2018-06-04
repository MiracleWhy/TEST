package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.MerchantListRequest;
import com.uton.carsokApi.controller.response.MerchantListResponse;
import com.uton.carsokApi.model.CarsokAcount;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author wangyj
 * @since 2017-11-07
 */
public interface CarsokAcountMapper extends BaseMapper<CarsokAcount> {

    Page<MerchantListResponse> getMerchantList(MerchantListRequest m);
}