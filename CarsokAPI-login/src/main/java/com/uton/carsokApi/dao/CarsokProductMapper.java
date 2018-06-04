package com.uton.carsokApi.dao;

import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.model.CarsokProduct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 商品 Mapper 接口
 * </p>
 *
 * @author csw
 * @since 2017-11-08
 */
public interface CarsokProductMapper extends BaseMapper<CarsokProduct> {

    /**
     * 库存列表数据查询
     * @param inventoryRequest
     * @return
     */
    List<InventoryResponse> getInventoryList(@Param("param") InventoryRequest inventoryRequest);

    /**
     * 在售数量查询
     * @param inventoryRequest
     * @return
     */
    Integer getOnSaleCount(@Param("param") InventoryRequest inventoryRequest);

    /**
     * 已售数量查询
     * @param inventoryRequest
     * @return
     */
    Integer getSaledCount(@Param("param") InventoryRequest inventoryRequest);

    /**
     * 获取车辆详情
     * @param id
     * @return
     */
    CarDetailsResponse getCarDetails(@Param("id") String id);

    /**
     * 获取车型图片列表
     * @param carId
     * @return
     */
    List<CarStockPic> getPicList(@Param("carId") int carId);

    Page<IntentionCarsListResponse> getintentionCarsList(@Param("param") Map<String,String> map);

    Page<IntentionCarsListResponse> getintentionCarsList_271(@Param("accountId") int accountId, @Param("param") IntentionCarsListRequest icarListRequest);

    Page<IntentionCarsListResponse> getintentionCarsListForAlly(@Param("accountIds") List<Integer> accountIds, @Param("param") IntentionCarsListRequest icarListRequest);

    List<CarSourceResponse> getCarsouceList(@Param("param") CarSourceSearchRequest carSourceSearchRequest,@Param("pn") Integer pn,@Param("ps") Integer ps);

    Page<TopCarSourceListResponse> getTopCarSourceLsit(TopCarSourceListRequest t);

    int getGrandTotalNew();

    Page<AllyCarListResponse> getAllyCarList(@Param("accountIds")List<Integer> accountIds,@Param("param") Map<String,String> map);

}