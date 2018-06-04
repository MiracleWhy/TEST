package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.GleefulReportDispatcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GleefulReportDispatcherMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(GleefulReportDispatcher record);

    int insertSelective(GleefulReportDispatcher record);

    GleefulReportDispatcher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GleefulReportDispatcher record);

    int updateByPrimaryKey(GleefulReportDispatcher record);

    int updateEnableByAccountId(@Param("accountId") String accountId,@Param("type")String type);

    int updateEnableBySharer(@Param("accountId")String accountId,@Param("sharer")String sharer,@Param("type")String type);

    List<GleefulReportDispatcher> getGleefulSharedList(@Param("accountId") String accountId,@Param("type")String type);
}