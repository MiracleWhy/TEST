package com.uton.carsokApi.dao;

import com.github.pagehelper.Page;
import com.uton.carsokApi.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
public interface TenureCustomerMapper {
    /**
     * 按天查询总条数
     * @return
     */
    int selectDayCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("perfect")int perfect);
    /**
     * 按月查询总条数
     * @return
     */
    int selectMonthCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("perfect")int perfect);
    /**
     * 按年查询总条数
     * @return
     */
    int selectYearCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("perfect")int perfect);
    /**
     * 按周查询总条数
     * @return
     */
    int selectWeekCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("perfect")int perfect);

    /**
     * 按月筛选
     * @param accountId
     * @param childId
     * @param perfect
     * @param month
     * @return
     */
    int selectByMonthCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("perfect")int perfect,@Param("month")String month);
    /**
     * 查询已完善客户总数
     * @param accountId
     * @param childId
     * @return
     */
    int selectYesCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("times")int times);

    int selectYesCountByMonth(@Param("accountId") int accountId, @Param("childId")int childId,@Param("month")String month);

    /**
     * 查询待完善客户总数
     * @param accountId
     * @param childId
     * @return
     */
    int selectNoCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("times")int times);

    int selectNoCountByMonth(@Param("accountId") int accountId, @Param("childId")int childId,@Param("month")String month);

    int selectBySaledCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("otherId")int otherId);

    List<CustomerTenure> selectBySaledMsg(@Param("p1")int p1, @Param("p2")int p2,@Param("accountId")int accountId,@Param("childId")int childId,@Param("otherId")int otherId);
    /**
     * 查询list
     * @param p1
     * @param p2
     * @param times
     * @param accountId
     * @param childId
     * @return
     */
    List<CustomerTenure> selectTenureMsg(@Param("p1")int p1, @Param("p2")int p2,@Param("times")int times,@Param("accountId")int accountId,@Param("childId")int childId,@Param("perfect")int perfect);

    List<CustomerTenure> selectTenureMsgByMonth(@Param("p1")int p1, @Param("p2")int p2,@Param("month")String month,@Param("accountId")int accountId,@Param("childId")int childId,@Param("perfect")int perfect);
    /**
     * 根据id查询tenure表
     * @param tid
     * @return
     */
    CustomerTenure selectTenureAll(@Param("tid")int tid);

    /**
     * 根据id查询car表
     * @param tcid
     * @return
     */
    CustomerCar selectCarAll(@Param("tcid") int tcid);

    /**
     * 根据关键字查询总数
     */
    int selectBySearch(@Param("selects")String selects,@Param("month")String month,@Param("accountId")int accountId,@Param("childId")int childId);

    int selectByNull(@Param("accountId")int accountId,@Param("childId")int childId,@Param("times")int time,@Param("month")String month);
    int selectByNotNull(@Param("accountId")int accountId,@Param("childId")int childId,@Param("times")int time,@Param("month")String month);

    /**
     * 根据关键字查询list
     * @param p1
     * @param p2
     * @param selects
     * @param accountId
     * @param childId
     * @return
     */
    List<CustomerTenure> selectSearchList(@Param("p1")int p1,@Param("p2") int p2,@Param("selects")String selects,@Param("month")String month,@Param("accountId")int accountId,@Param("childId")int childId);

    List<CustomerTenure> selectNullList(@Param("p1")int p1,@Param("p2") int p2,@Param("accountId")int accountId,@Param("childId")int childId,@Param("times")int time,@Param("month")String month);
    List<CustomerTenure> selectNotNullList(@Param("p1")int p1,@Param("p2") int p2,@Param("accountId")int accountId,@Param("childId")int childId,@Param("times")int time,@Param("month")String month);
    /**
     * 根据tid删除tenure表
     * @param tid
     * @return
     */
    int deleteByTid(@Param("tid")int tid);

    /**
     * 根据tcid删除tenurecar表
     * @param tcid
     * @return
     */
    int deleteByTcid(@Param("tcid")int tcid);

    /**
     * 根据tid查询tenure表
     * @param tid
     * @return
     */
    CustomerTenure selectTenureById(@Param("tid")int tid);

    /**
     * 根据tcid查询tenurecar表
     * @param tcid
     * @return
     */
    CustomerCar selectTenureCarById(@Param("tcid")int tcid);

    /**
     * 添加CustomerTenure
     * @param customerTenure
     * @return
     */
    int insertTenure(CustomerTenure customerTenure);

    /**
     * 添加CustomerCar
     * @param customerCar
     * @return
     */
    int insertTenureCar(CustomerCar customerCar);

    int insertWelfare(Welfare welfare);

    int updateWelfare(Welfare welfare);

    Welfare selectCarWelfareByMobile(@Param("mobile")String mobile);

    int updateTenureMsg(CustomerTenure customerTenure);

    List<TenureTask> selectTenureThree();

    CustomerTenure selectNewMsgBycustPhone(@Param("custPhone")String custPhone,@Param("accountId")int accountId,@Param("childId")int childId);

    List<CustomerTenure> selectCarListByCustPhone(@Param("custPhone")String custPhone,@Param("accountId")int accountId,@Param("childId")int childId);

    CustomerTenure selectCustMsgByCarId(int tenurecarId);

    int updateOldByNew(CustomerCar customerCar);

    int updateNewCarIsDelete(@Param("id") int id);

    int selectByProductId(@Param("id")Integer id);

    int selectNum(@Param("accountId")int accountId, @Param("childId")int childId, @Param("type")int type);

    Page<CustomerTenure> selectListNew(@Param("accountId")int accountId, @Param("childId")int childId, @Param("type")String type, @Param("selects")String selects, @Param("compeleteStatus")String compeleteStatus, @Param("dateStatus")String dateStatus, @Param("buyStatus")String buyStatus);

    List<TenureCarFollow> selectFollowMessage(@Param("tcid")int tcid);

    int saveFollowMessage(TenureCarFollow tenureCarFollow);

    int updateStatusByType(@Param("tenurecarId")Integer tenurecarId, @Param("followType")Integer followType);

    int selectByTenurecarId(@Param("tcid")Integer tcid);
}
