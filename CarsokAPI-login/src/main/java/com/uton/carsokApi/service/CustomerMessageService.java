package com.uton.carsokApi.service;

import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.StoreRequest;
import com.uton.carsokApi.model.Customer;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/30.
 */
public interface CustomerMessageService {
    /**
     * 门店筛选通用方法
     * @param map
     * @return
     */
    Page<Customer> selectCustList(Map<String,String> map);

    /**
     * 门店搜索/年月日筛选方法
     * @param select
     * @return
     */
    Page<Customer> selectCustListBySearchOrMonth(String select,String mobile,String pc);

    /**
     * 根据id查询客户详细信息
     * @param id
     * @return
     */
    Map selectCustMsgById(String id);

    /**
     * 查询是否过期的总数
     * @param mobile
     * @return
     */
    Map selectThreeOrSevenCount(String mobile);
}
