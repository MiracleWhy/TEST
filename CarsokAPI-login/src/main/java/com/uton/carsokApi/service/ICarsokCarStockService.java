package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.controller.request.AddNewCarRequest;
import com.uton.carsokApi.controller.request.CarStockListRequest;
import com.uton.carsokApi.controller.request.CarStockRequest;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.model.CarsokCarStock;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author csw
 * @since 2017-11-08
 */
public interface ICarsokCarStockService extends IService<CarsokCarStock> {

    /**
     * 车辆入库
     * @param acr
     * @return
     */
    Integer addNewCar(AddNewCarRequest acr);


    /**
     * 入库管理首页列表查询
     *
     * @param clr
     * @return
     */
    CarStockListResponse getCarStockList(CarStockListRequest clr);

    /**
     * 品牌列表查询
     *
     * @param accountId
     * @return
     */
    BrandListResponse getBrandList(String accountId);

    /**
     * 车型列表查询
     *
     * @param carBrandId
     * @return
     */
    SeriesListResponse getSeriesList(String accountId,Integer carBrandId);

    /**
     * 车型库编辑查询
     * @param accountId
     * @param id
     * @return
     */
    CarStockInfoResponse getCarStockInfo(String accountId,Integer id);

    void delCarStock(String accountId,Integer id);

    /**
     * 获取原有品牌列表
     *
     * @return
     */
    List<OldBrandResponse> getOldBrandList();


    /**
     * 车型库新增/编辑
     *
     * @param carStockRequest
     * @return
     */
    Boolean editNewCarStock(CarStockRequest carStockRequest);

}
