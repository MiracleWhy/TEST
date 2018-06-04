package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.ModularApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */
public interface ModularApplyMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ModularApply record);

    int insertSelective(ModularApply record);

    ModularApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModularApply record);

    ModularApply selectByTypeAndAccountId(@Param("accountId") int accountId,@Param("type") String type);

    /**
     * 查询模块开通情况
     * @param accountId
     * @return
     */
    List<String> selectAllApplyList(@Param("accountId") int accountId);
}
