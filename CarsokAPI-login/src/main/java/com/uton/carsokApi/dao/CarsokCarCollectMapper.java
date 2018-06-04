package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uton.carsokApi.controller.request.CarCollectRequest;
import com.uton.carsokApi.controller.response.CarCollectListResponse;
import com.uton.carsokApi.controller.response.ShareAccountInfo;
import com.uton.carsokApi.model.CarsokCarCollect;
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
public interface CarsokCarCollectMapper extends BaseMapper<CarsokCarCollect> {

    /**
     * 获取车源收藏列表
     * @param carCollectRequest
     * @return
     */
    List<CarCollectListResponse> getCarCollectList(@Param("param") CarCollectRequest carCollectRequest);

    /**
     * 获取列表数据条数
     * @param accountId
     * @param childId
     * @param searchName
     * @return
     */
    Integer getListCount(@Param("accountId") Integer accountId,@Param("childId") Integer childId,@Param("searchName") String searchName);

    /**
     * 查询车源是否被收藏
     * @param accountId
     * @param childId
     * @param collectCarId
     * @return
     */
    Integer isCollectCar(@Param("accountId") Integer accountId,@Param("childId") Integer childId,@Param("collectCarId") Integer collectCarId);

    /**
     * 查询分享车行信息
     * @param accountId
     * @return
     */
    ShareAccountInfo getAcountInfo(@Param("accountId") Integer accountId);

}