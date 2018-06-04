package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.controller.response.CarsokCustomerResponse;
import com.uton.carsokApi.model.CarsokCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
public interface CarsokCustomerMapper extends BaseMapper<CarsokCustomer> {
    /**
     * 查询当前登录人角色
     * @param childId
     * @return
     */
    Page<String> selectPowerByChildMobile(@Param("childId") int childId);

    /**
     * tab页查询
     * @param type
     * @param accountId
     * @param childId
     * @return
     */
    List<CarsokCustomerResponse> selectLatentListByTab(@Param("type") int type,@Param("accountId") int accountId,@Param("childId") int childId);

    /**
     * 搜索查询
     * @param select
     * @param accountId
     * @param childId
     * @return
     */
    List<CarsokCustomerResponse> selectLatentListBySearchKey(@Param("select") String select, @Param("accountId") int accountId, @Param("childId") int childId);

    /**
     * 筛选查询
     * @param level
     * @param budget
     * @param childId
     * @param accountId
     * @return
     */
    List<CarsokCustomerResponse> selectLatentListByScreen(@Param("times") int times,@Param("levels") List<String> level, @Param("sources") List<String> source, @Param("budgets") List<String> budget, @Param("childIds") List<Integer> childId, @Param("accountId") int accountId, @Param("power") String power,@Param("childId") int child);

    /**
     * 查询所有待办数量
     * @param accountId
     * @param childId
     * @return
     */
    int selectTabCount(@Param("accountId") int accountId, @Param("childId") int childId,@Param("type") int type);

    /**
     * 定时任务
     * @param visitStatus
     * @param dayStatus
     * @return
     */
    int updateVisitStatus(@Param("visitStatus") int visitStatus,@Param("dayStatus") int dayStatus);

    /**
     * 逾期定时任务
     * @return
     */
    int updateOverdue();

    /**
     * 通过主键查询客户
     * @param id
     * @return
     */
    CarsokCustomerResponse selectTaskById(@Param("id") int id);

    /**
     * 通过主键改变客户级别
     * @param id
     * @param level
     * @return
     */
    int updateCustLevelById(@Param("id") int id ,@Param("level") String level);
}