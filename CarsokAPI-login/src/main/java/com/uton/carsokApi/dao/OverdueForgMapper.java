package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.ChildNameAndId;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20/020.
 */
public interface OverdueForgMapper {
//子账户id和name
    List<ChildNameAndId> selectChild(@Param("accountPhone") String accountPhone);
    //潜客逾期
              Integer qianke(@Param("accountId") String accountId,@Param("childId") String childId);
    //本日潜客allId
    Integer allQianke(@Param("account") String account) ;


    //查询单个子账户的名字
    String selectOneChildName(@Param("phone")String phone);


}
