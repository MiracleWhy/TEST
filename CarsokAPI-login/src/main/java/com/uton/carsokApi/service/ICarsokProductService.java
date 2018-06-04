package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokProduct;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author csw
 * @since 2017-11-08
 */
public interface ICarsokProductService extends IService<CarsokProduct> {

    /**
     * 库存列表数据查询
     * @param inventoryRequest
     * @return
     */
    CarsokProductResponse getInventoryList(InventoryRequest inventoryRequest);

    /**
     * 获取车辆详情
     * @param id
     * @return
     */
    CarDetailsResponse getCarDetails(String id);

    /**
     * 编辑车辆信息
     * @param newCarDetailsRequest
     * @return
     */
    Boolean editCarDetails(NewCarDetailsRequest newCarDetailsRequest);

    /**
     * 删除车辆信息
     * @param id
     * @return
     */
    Boolean delCarDetails(String id);

    /**
     * 更新车辆已售状态
     * @param id
     * @return
     */
    Boolean updateCarSaleStatus(String id);

    /**
    * @author zhangdi
    * @date 2017/11/10 11:27
    * @Description: 通过product_NO carid isnot null 获取车辆信息
    */

    CarsokProduct selectByProductNo(Integer id );

    /**
    * @author zhangdi
    * @date 2017/11/10 14:49
    * @Description: 添加售出人
    */

    boolean updateSaledPeople(Integer id, String phone);

    /**
    * @author zhangdi
    * @date 2017/12/4 18:10
    * @Description: 意向车型
    */
   com.github.pagehelper.Page<IntentionCarsListResponse> getIntentionCarsList(String accountId, int pageSize, int pageNum) ;

    /**
    * @author zhangdi
    * @date 2018/1/22 15:40
    * @Description: 车源列表
    */
    List<CarSourceResponse> getCarsouceList(CarSourceSearchRequest carSourceSearchRequest, Acount acount,Integer pageNum,Integer pageSize);
    /**
    * @author zhangdi
    * @date 2018/1/22 15:40
    * @Description: 车源置顶列表
    */
    Page<TopCarSourceListResponse> getTopCarSourceList(TopCarSourceListRequest t);

    /**
    * @author zhangdi
    * @date 2018/1/24 10:50
    * @Description:title数据
    */
    TitleDataResponse titleData();
    /**
    * @author zhangdi
    * @date 2018/1/24 14:40
    * @Description: 定时任务清楚今日浏览数据
    */
    boolean clearDayViewCount();

    boolean addBrowse(int productId);

    /**
    * @author zhangD
    * @date 2018/2/1 9:57
    * @Description: 二手车数量统计
    */

    int getTotalCount();

    /**
    * @author zhangD
    * @date 2018/2/27 15:00
    * @Description: 联盟车型
    */

    Page<AllyCarListResponse> getAllyCarList(List<Integer> accountIds, AllyCarListRequest request);

    com.github.pagehelper.Page<IntentionCarsListResponse> getIntentionCarsList_271(Acount acount,IntentionCarsListRequest intentionCarsListRequest);
    com.github.pagehelper.Page<IntentionCarsListResponse> intentionCarList(Acount acount,IntentionCarsListRequest intentionCarsListRequest);

}
