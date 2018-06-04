package com.uton.carsokApi.dao;
import com.uton.carsokApi.model.*;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.uton.carsokApi.model.Bussines;

/**
 * Created by xin_mz on 2017/6/28
 * 市场拓展管理
 */

public interface BusinesMapper {
	/**
     * 添加拜访信息
     * @param customer
     * @return
     */
    int insertCustomer(Bussines customer);
    /**
     * 修改拜访信息
     * @param customer
     * @return
     */

	int updateBussines(Bussines customer);


    /**
     *按月查询条数
     ** @param accountId
     * @param childId
     * @param months
     * @return
     */

    int selectCountByMonth(@Param("accountId")int accountId, @Param("childId")int childId,@Param("months") String months);


    /**
     *按当月查询条数
     * @param accountId
     * @param childId
     * @return
     */
    int selectMonthCount(@Param("accountId")int accountId,@Param("childId")int childId);

    /**
 * 按当星期查询总记录数
 * @param accountId
 * @param childId
 * @return
 */
    int selectWeekCount(@Param("accountId")int accountId,@Param("childId")int childId);


    /**
     * 按当天查询总记录数
     * @param accountId
     * @param childId
     * @return
     */

    int selectDayCount(@Param("accountId")int accountId,@Param("childId")int childId);
    /**
     * 按查询总记录数
     * @param accountId
     * @param childId
     * @return
     */
    int selectAll(@Param("accountId")int accountId,@Param("childId")int childId);
    /**
     * 查询用户信息
     * @param p1
     * @param p2
     * @param times
     * @param accountId
     * @param childId
     * @return
     */
    List<Bussines> selectBussinesMsg(@Param("p1")int p1, @Param("p2")int p2,@Param("times")int times, @Param("accountId")int accountId, @Param("childId")int childId);

    /**
     * 查询用户信息根据月份
     * @param p1
     * @param p2
     * @param times
     * @param accountId
     * @param childId
     * @param months
     * @return
     */
    List<Bussines> selectBussinesMsgByMonth(@Param("p1")int p1, @Param("p2")int p2,@Param("times")int times, @Param("accountId")int accountId, @Param("childId")int childId,@Param("months") String months);

    /**
     * 搜索
     * @param selects
     * @return
     */
    int selectBySearchKey(@Param("selects")String selects,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 搜索分页
     * @param p1
     * @param p2
     * @param selects
     * @return
     */
    List<Bussines> queryBusinesListBysearchkey(@Param("p1")int p1, @Param("p2")int p2, @Param("selects")String selects, @Param("month")String month, @Param("accountId")int accountId, @Param("childId")int childId);


    /**
     * 根据id查询
     * @param id
     * @return
     */
    Bussines selecByid(@Param("id") int id);


    /**
     * 根据id查询Picture
     * @return
     */
    String selecByPicture(String id);


    /**
     * 修改图片
     * @return
     */

    void updatePicture (@Param("code")String code, @Param("id")String id);

    int selectCount(@Param("times")String times, @Param("accountId")int accountId, @Param("childId")int childId, @Param("month")String month, @Param("selects")String selects);

    List<Bussines> selectBusinessList(@Param("p1")int p1, @Param("p2")int p2, @Param("times")String times, @Param("accountId")int accountId, @Param("childId")int childId, @Param("month")String month, @Param("selects")String selects);

    int addFollowupInfo(FollowupInfoModel followupInfo);

    List<FollowupInfoModel> getFollowupInfoList(FollowupInfoModel vo);

    List<ChildNameId> getBussWorker(@Param("userLeaveName") String userLeaveName,@Param("mobile") String mobile);

    List<MarketMove> getmarketMave(@Param("id") int id,@Param("p1") int p1,@Param("p2")int p2);

    List<Integer> selectUpdateUserMove(@Param("name")String name,@Param("mobile") String mobile);

    void updateUserMove(@Param("childId") Integer childId,@Param("list") List<String> id);

    String selectZ(@Param("mobile") String mobile);

    List<String> selectZs(@Param("mobile") String mobile);

    Integer getmarketMaveCount(@Param("id") Integer id);

    List<String> selectFowType();

    List<AddNextVisit> selectaddNextVisit(@Param("id")String id,@Param("followupType")String followupType);

    List<CarsokPmsForfigure> findPageCondition(@Param("page") int page,@Param("size") int size);

    String findPicture(@Param("id") int id);

}
