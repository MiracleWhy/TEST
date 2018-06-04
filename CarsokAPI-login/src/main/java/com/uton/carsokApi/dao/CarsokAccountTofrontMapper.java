package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uton.carsokApi.controller.response.AccountTofrontResponse;
import com.uton.carsokApi.model.CarsokAccountTofront;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface CarsokAccountTofrontMapper extends BaseMapper<CarsokAccountTofront> {

    /**
     * 置顶车行记录查询
     * @param accountId
     * @param childId
     * @return
     */
    AccountTofrontResponse accountTofront(@Param("accountId") Integer accountId,@Param("childId") Integer childId);

}