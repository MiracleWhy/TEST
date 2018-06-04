package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.UserSign;
import org.apache.ibatis.annotations.Param;

public interface UserSignMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserSign record);

    int insertSelective(UserSign record);

    UserSign selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserSign record);

    int updateByPrimaryKey(UserSign record);

    UserSign selectUserSignByAccountId(@Param("accountId")String accountId);

    int selectUserContinuitySign(@Param("accountId")String accountId);

    int updateUserSign(@Param("accountId")String accountId);

    int updatePresentTimes(@Param("accountId")String accountId,@Param("times")Integer times);


}