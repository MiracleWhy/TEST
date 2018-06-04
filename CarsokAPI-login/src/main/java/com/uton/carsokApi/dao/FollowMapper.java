package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.FollowVoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by SEELE on 2017/11/11.
 */
public interface FollowMapper {
         Integer getFollowOneCount(@Param("accountId") Integer accountId, @Param("childId") Integer childId,@Param("startDate") String startDate,@Param("endDate") String endDate);
         /**获取跟进报表第一行数据**/
         Integer getFollowTotalCount(@Param("accountId") Integer accountId, @Param("childs") List childs, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
