package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.request.DRReceptionDetailResponse;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.controller.request.DRReceptionResponse;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/19.
 */
public interface DailyReportMapper {

    /**
     * 获取今日收车列表
     */
    List<AcqCarMsg> selectAcqCarMsg(@Param("accountId") int accountId, @Param("date") String date);

    /**
     * 获取今日整备完毕列表
     */
    List<ZbMsg> selectZbMsg(@Param("accountId") int accountId, @Param("date") String date);

    /**
     * 获取今日接待列表
     */
    List<DRReceptionResponse> getReceptionList(@Param("date") String date, @Param("accountId") String accountId);

    /**
     * 获取接待详情
     */
    List<DRReceptionDetailResponse> getReceptionDetail(@Param("date") String date, @Param("accountId") String accountId, @Param("childId") String childID);

    int insertDailyReport(DailyReport dailyReport);

    int selectCount(@Param("reporter") String reporter);

    List<DailyReport> selectDailyReport(@Param("reporter") String reporter);

    DailyReport selectDailyReportMsg(@Param("id") int id);

    int selectCountByAccount(@Param("childLists") List<String> childLists);

    List<DailyReport> selectDailyReportByAccount(@Param("childLists") List<String> childLists, @Param("p1") int p1, @Param("p2") int p2);

    List<DailyReport> selectDailyReportByAccountZjl(@Param("zjlPhone") String zjlPhone, @Param("childLists") List<String> childLists, @Param("p1") int p1, @Param("p2") int p2);

    int updateIsRead(@Param("id") int id);

    int insertZjlIsRead(@Param("id") int id, @Param("zjlPhone") String zjlPhone);

    int selectCountByScreen(@Param("reportDate") java.util.Date reportDate, @Param("childPhone") String childPhone, @Param("roleName") String roleName, @Param("childLists") List<String> childLists);

    List<DailyReport> selectMsgByScreen(@Param("p1") int p1, @Param("p2") int p2, @Param("reportDate") java.util.Date reportDate, @Param("childPhone") String childPhone, @Param("roleName") String roleName, @Param("childLists") List<String> childLists);

    String selectReportedCount(@Param("reportDate") java.util.Date reportDate, @Param("childLists") List<String> childLists);

    List<ChildAccount> selectReportedList(@Param("reportDate") java.util.Date reportDate, @Param("childLists") List<String> childLists);

    List<DailyReport> selectMsgByScreenZjl(@Param("zjlPhone") String zjlPhone, @Param("p1") int p1, @Param("p2") int p2, @Param("reportDate") java.util.Date reportDate, @Param("childPhone") String childPhone, @Param("roleName") String roleName, @Param("childLists") List<String> childLists);

    List<ZbRole> selectRoleList();

    int insetDailyReportZan(DailyReportZan dailyReportZan);

    List<DailyReportZan> selectCarIdByAccountPhone(@Param("accountPhone") String accountPhone, @Param("departmentType") String departmentType, @Param("fabulousPhone") String fabulousPhone);

    String selectChildAccountMobile(@Param("id") int id);

    String selectAccountMobile(@Param("mobile") String mobile);

    int insertDailyReportZan(DailyReportZan dailyReportZan);

    int updateZanStatus(@Param("id") int id);

    List<DailyReportZan> selectZanListByDepartmentType(@Param("accountPhone") String accountPhone, @Param("departmentType") String departmentType, @Param("createTime") java.util.Date createTime);

    String selectCarSharedNum(@Param("accountId") Integer accountId, @Param("searchDay") String searchDay);

    String selectWordSharedNum(@Param("accountId") Integer accountId, @Param("searchDay") String searchDay);

    String selectPaintSharedNum(@Param("accountId") Integer accountId, @Param("searchDay") String searchDay);

    String selectNewGleefulNum(@Param("accountId") Integer accountId, @Param("searchDay") String searchDay);

    String selectCheckedCarNum(@Param("accountId") Integer accountId, @Param("searchDay") String searchDay);

    String selectAllCarNum(@Param("accountId") Integer accountId, @Param("searchDay") String searchDay);

    List<OtherShareContent> selectPaihangList(@Param("accountId") int accountId, @Param("accountPhone") String accountPhone, @Param("merchantName") String merchantName, @Param("shareTime") java.util.Date shareTime, @Param("shareType") String shareType);

    BigDecimal getDayProfit(@Param("accountId") int accountId, @Param("date") String date);

    Integer queryDailyRepPubCount(@Param("childs") List<ChildAccount> childs);

    Integer queryDailyRepPubByReporter(@Param("childPhone") String childAccountMobile);

    /**
     * 《今日战报》--卖车--日期参数 获取卖车数
     **/
    Integer countCarSaleByDate(@Param("accountId") int accountId, @Param("children") List<ChildAccount> children, @Param("date") String date, @Param("type") String type);

    Double sumCarSaleAmount(@Param("accountId") int accountId, @Param("children") List<ChildAccount> children, @Param("date") String date, @Param("type") String type);

    Integer countCollectionByDate(@Param("accountId") int accountId, @Param("children") List<ChildAccount> children, @Param("date") String date, @Param("type") String type);

    Double sumAcqCostByDate(@Param("accountId") int accountId, @Param("children") List<ChildAccount> children, @Param("date") String date, @Param("type") String type);

    Integer countRestoreByDate(@Param("account") String account, @Param("date") String date, @Param("type") String type);

    Double sumRestoreCostByDate(@Param("account") String account, @Param("date") String date, @Param("type") String type);

    /**获取本日接待人数**/
    Integer getReceptionCount(@Param("accountId") int accountId, @Param("children") List<ChildAccount> children, @Param("date") String date, @Param("type") String type);

    List<CustomerTenureCar> selectCustomerTenureCarList(@Param("accountId") int accountId, @Param("children") List<ChildAccount> children, @Param("date") String date, @Param("type") String type);

    Double queryAcqCostByProductId(@Param("productId") int productId);

    Double queryRestoreCostByProductId(@Param("productId") int productId);

    String queryPicPathByProductId(@Param("productId") int productId);

    List selectQx(@Param("childPhone") String childPhone);
}