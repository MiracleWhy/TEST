package com.uton.carsokApi.dao;

import com.github.pagehelper.Page;
import com.uton.carsokApi.model.Customer;
import com.uton.carsokApi.model.CustomerFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
public interface CustomerMessageMapper {
    /**
     * 门店筛选通用方法
     * @param times
     * @param accountId
     * @param childId
     * @param xxly
     * @param khjb
     * @param dfzt
     * @param gmys
     * @return
     */
    Page<Customer> selectCustList(@Param("times")int times, @Param("accountId")int accountId, @Param("childId")int childId, @Param("xxly")String xxly, @Param("khjb")String khjb, @Param("dfzt")int dfzt, @Param("gmys")String gmys,@Param("type") String type);

    /**
     * 门店搜索/年月日筛选
     * @param select
     * @param accountId
     * @param childId
     * @return
     */
    Page<Customer> selectCustListBySearchOrMonth(@Param("select")String select,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 根据id查询客户详细信息
     * @param id
     * @return
     */
    Customer selectCustAllMsgById(@Param("id") String id);

    /**
     * 根据id查询客户跟进信息列表
     * @param id
     * @return
     */
    List<CustomerFlow> seletCustFlowList(@Param("id") String id);

    /**
     * 查询数量
     * @param accountId
     * @param childId
     * @param type
     * @return
     */
    int selectThreeOrSevenCount(@Param("accountId")int accountId,@Param("childId")int childId,@Param("type") int type);
}
