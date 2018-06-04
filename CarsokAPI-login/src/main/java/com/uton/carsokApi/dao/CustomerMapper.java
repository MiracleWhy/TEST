package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.PotCusVoResponse;
import com.uton.carsokApi.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public interface CustomerMapper {
    /**
     * 查询总记录数
     * @param accountId
     * @param childId
     * @return
     */
    int selectCount(@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys);

    /**
     * 年月查询
     * @param accountId
     * @param childId
     * @param xxly
     * @param khjb
     * @param dfzt
     * @param gmys
     * @param month
     * @return
     */
    int selectCountByMonth(@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys,@Param("month") String month);

    /**
     * 按年月查询List
     * @param p1
     * @param p2
     * @param month
     * @param accountId
     * @param childId
     * @param xxly
     * @param khjb
     * @param dfzt
     * @param gmys
     * @return
     */
    List<Customer> selectCustMsgByMonth(@Param("p1")int p1, @Param("p2")int p2,@Param("month")String month,@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys);
    /**
     * 按当天查询总记录数
     * @param accountId
     * @param childId
     * @return
     */
    int selectDayCount(@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys);

    /**
     * 按当星期查询总记录数
     * @param accountId
     * @param childId
     * @return
     */
    int selectWeekCount(@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys);

    /**
     * 按当月查询总记录数
     * @param accountId
     * @param childId
     * @return
     */
    int selectMonthCount(@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys);

    /**
     * 搜索
     * @param selects
     * @return
     */
    int selectBySearchKey(@Param("selects")String selects,@Param("month")String month,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 添加客户
     * @param customer
     * @return
     */
    int insertCustomer(Customer customer);

    /**
     * 查询用户信息
     * @param p1
     * @param p2
     * @param times
     * @return
     */
    List<Customer> selectCustomerMsg(@Param("p1")int p1, @Param("p2")int p2,@Param("times")int times,@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys);

    /**
     * 搜索分页
     * @param p1
     * @param p2
     * @param selects
     * @return
     */
    List<Customer> querycustomerListBysearchkey(@Param("p1")int p1, @Param("p2")int p2,@Param("selects")String selects,@Param("month")String month,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 修改客户跟进
     * @param custTrack
     * @param custPhone
     * @param custName
     * @return
     */
    int updateTrack(@Param("custTrack")String custTrack,@Param("custPhone")String custPhone,@Param("custName")String custName,@Param("custId") int custId);

    /**
     * 通过电话和姓名查询
     * @param custName
     * @param custPhone
     * @return
     */
    List<Customer> selectMsgByNameOrPhone(@Param("custName")String custName,@Param("custPhone")String custPhone,@Param("custId")int custId);

    /**
     * 添加跟进信息
      */
    int insertFlow(@Param("custId")int custId,@Param("custFlow")String custFlow,@Param("status")int status,@Param("remind") String remind,@Param("custCome")int custCome,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 添加跟进后修改跟进标识
     * @param id
     * @return
     */
    int updateThreeSevenById(@Param("id") int id,@Param("customerStatus") int customerStatus);

    /**
     * 根据customerId查询客户所有跟进信息
     */
    List<CustomerFlow> selectAllFlows(@Param("custId")int custId);

    /**
     * 修改到访状态
     */
    int updateStatus(@Param("customerStatus")String customerStatus,@Param("custId")int custId,@Param("custCome")int custCome);

    /**
     * 根据customerId查询跟进信息
     */
    List<CustomerFlow> selectFlowMsgByCustomerId(@Param("custId")int custId);

    /**
     * 查询客户到访状态
     */
    int selectCustomerStatus(@Param("custId")int custId);

    int selectScreenCount(@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys,@Param("time")int time);
    /**
     * 信息筛选by所有
     */
    List<Customer> ScreenMessageAll(@Param("p1") int p1,@Param("p2") int p2,@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("khjb")String khjb,@Param("dfzt")int dfzt,@Param("gmys")String gmys,@Param("time") int time);


    List<StoreRemind> selectManyMsg();

    int selectByRemindCount(@Param("otherId")int otherId,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 消息中心跳转门店
     * @param p1
     * @param p2
     * @param otherId
     * @param accountId
     * @param childId
     * @return
     */
    List<Customer> selectByRemindMsg(@Param("p1") int p1,@Param("p2") int p2,@Param("otherId")int otherId,@Param("accountId")int accountId,@Param("childId")int childId);

    Customer selectByCustPhone(@Param("custPhone")String custPhone,@Param("accountId")int accountId);

    List<Customer> selectCustIf(@Param("custPhone")String custPhone,@Param("accountId")int accountId,@Param("childId")int childId);

    Customer selectByUpdate(@Param("id") int id);

    int updateCustomerMsg(Customer customer);
    Customer selectByFollow(@Param("id") int id);

    /**
     * 修改拜访信息
     * @param customer
     * @return
     */

    int updateCustomer(Customer customer);

    int selectListByCustPhoneCount(@Param("custPhone")String custPhone,@Param("accountId")int accountId,@Param("childId")int childId);

    List<Customer> selectListByCustPhone(@Param("p1") int p1,@Param("p2") int p2,@Param("custPhone")String custPhone,@Param("accountId")int accountId,@Param("childId")int childId);

    int updateCustomerFlow(int customerId);

    PotCusVoResponse queryPotCusVo(@Param("accountId") Integer id, @Param("childId") Integer childId, @Param("startDate")String startDate, @Param("endDate")String endDate, @Param("type")Integer type);

    Integer queryDailyRegionCountByDate(@Param("accountId") Integer accountId
            ,@Param("childAccounts") List<CarsokChildAccount> childAccounts
            ,@Param("startDate")String startDate
            ,@Param("endDate")String endDate);

    /**查询区域及对应客户数**/
    List<KeyValuePair> queryRegionOfSomeCustomers(@Param("accountId") Integer accountId
            ,@Param("childAccounts") List<CarsokChildAccount> childAccounts
            ,@Param("startDate")String startDate
            ,@Param("endDate")String endDate);

    /**查询某已知区域对应的客户数**/
    Integer countCustomerNumOfRegion(@Param("accountId") Integer accountId
            ,@Param("childAccounts") List<CarsokChildAccount> childAccounts
            ,@Param("startDate")String startDate
            ,@Param("endDate")String endDate
            ,@Param("region")String region
            ,@Param("type")Integer type);

    /**个人潜客总数**/
    Integer countPotCusTotalOfOne(@Param("accountId") Integer accountId
            ,@Param("childId")Integer childId
            ,@Param("date")Date date);
}
