package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uton.carsokApi.controller.request.CarCollectRequest;
import com.uton.carsokApi.controller.response.AccountCollectListResponse;
import com.uton.carsokApi.model.CarsokAccountCollect;
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
public interface CarsokAccountCollectMapper extends BaseMapper<CarsokAccountCollect> {
    /**
     * 获取车行收藏列表
     * @param carCollectRequest
     * @return
     */
    List<AccountCollectListResponse> getAccountCollectList(@Param("param") CarCollectRequest carCollectRequest);

    /**
     * 获取里列表数据条数
     * @param accountId
     * @param childId
     * @param searchName
     * @return
     */
    Integer getListCount(@Param("accountId") Integer accountId,@Param("childId") Integer childId,@Param("searchName") String searchName);

    /**
     * 查询车行是否被收藏
     * @param accountId
     * @param childId
     * @param collectAccountId
     * @return
     */
    Integer isCollectAccount(@Param("accountId") Integer accountId,@Param("childId") Integer childId,@Param("collectAccountId") Integer collectAccountId);

}