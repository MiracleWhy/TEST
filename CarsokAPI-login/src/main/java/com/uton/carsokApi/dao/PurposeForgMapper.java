package com.uton.carsokApi.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/11/10/010.
 */
public interface PurposeForgMapper {
    Integer selectCustomerCountN(@Param("childId") Integer childId);
    Integer selectCustomerCountH(@Param("childId") Integer childId);
    Integer selectCustomerCountA(@Param("childId") Integer childId);
    Integer selectCustomerCountB(@Param("childId") Integer childId);
    Integer selectCustomerCountC(@Param("childId") Integer childId);
    Integer selectCustomerCountFO(@Param("childId") Integer childId);
    Integer selectCustomerCountF(@Param("childId") Integer childId);


    Integer selectAccountCustomerCountN(@Param("accountId") Integer accountId);
    Integer selectAccountCustomerCountH(@Param("accountId") Integer accountId);
    Integer selectAccountCustomerCountA(@Param("accountId") Integer accountId);
    Integer selectAccountCustomerCountB(@Param("accountId") Integer accountId);
    Integer selectAccountCustomerCountC(@Param("accountId") Integer accountId);
    Integer selectAccountCustomerCountFO(@Param("accountId") Integer accountId);
    Integer selectAccountCustomerCountF(@Param("accountId") Integer accountId);

    Integer childIds(@Param("phone") String phone);
    String selectChildName(@Param("ids") Integer id);
}
