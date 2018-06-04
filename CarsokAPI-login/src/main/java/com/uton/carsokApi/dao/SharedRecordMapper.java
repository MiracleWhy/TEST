package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.SharedRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SharedRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SharedRecord record);

    int insertSelective(SharedRecord record);

    SharedRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SharedRecord record);

    int updateByPrimaryKey(SharedRecord record);

    List<SharedRecord> getSharedRecordList(@Param("accountId")String accountId,@Param("shareId")String shareId);

    String getSharerName(@Param("accountId")String accountId,@Param("sharer")String sharer);
}