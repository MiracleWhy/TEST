package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.GleefulReportResponse;
import com.uton.carsokApi.model.GleefulReport;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GleefulReportMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(GleefulReport record);

    int insertSelective(GleefulReport record);

    GleefulReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GleefulReport record);

    int updateByPrimaryKey(GleefulReport record);

    List<GleefulReportResponse> getGleefulReportList(@Param("accountId")String accountId,@Param("sharer")String sharer, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    Integer getGleefulSharedRecordCount(@Param("sharer")String sharer);

    /**
     * Param : accountId,start,end
     */
    GleefulReport selectFirstReportByAccountId(Map param);
}