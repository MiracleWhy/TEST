package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.CollectIdentityResponse;
import com.uton.carsokApi.controller.response.PermissionResponse;
import com.uton.carsokApi.model.AcquisitionCar;
import com.uton.carsokApi.model.AcquisitionCarPricing;
import com.uton.carsokApi.model.AcquisitionConsult;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
public interface AcquisitionCarMapper {

    /**
     * 添加信息
     * @param acquisitionCar
     * @return
     */
    int insertMessage(AcquisitionCar acquisitionCar);



    /**
     * 添加协商价格
     * @param acquisitionConsult
     * @return
     */
    int insertConsult(AcquisitionConsult acquisitionConsult);

    /**
     * 修改信息
     * @param acquisitionCar
     * @return
     */
    int updateAcquisitionCar(AcquisitionCar acquisitionCar);

    int deleteConsult(@Param("acquisitionId") int acquisitionId);

    /**
     * 查询信息
     * @param map
     * @return
     */
	List<AcquisitionCar> selectAcquisitionCar(Map map);


    /**
     * 查询总数
     */
    int selectCount(@Param("accountId") int accountId,@Param("childId") int childId,@Param("isDeal") int isDeal,@Param("xxly")String xxly,@Param("ycsj")String ycsj,@Param("px")int px);

    /**
     * 查询列表
     * @param p1
     * @param p2
     * @param accountId
     * @param childId
     * @param isDeal
     * @return
     */
    List<AcquisitionCar> selectAcquisitionMsg(@Param("p1")int p1, @Param("p2")int p2,@Param("accountId")int accountId,@Param("childId")int childId,@Param("isDeal")int isDeal,@Param("xxly")String xxly,@Param("ycsj")String ycsj,@Param("px")int px);

    /**
     * 查询成交总数
     * @param accountId
     * @param childId
     * @param isDeal
     * @return
     */
    int selectdealY(@Param("accountId")int accountId,@Param("childId")int childId,@Param("isDeal")int isDeal);

    /**
     * 查询未成交总数
     * @param accountId
     * @param childId
     * @param isDeal
     * @return
     */
    int selectdealN(@Param("accountId")int accountId,@Param("childId")int childId,@Param("isDeal")int isDeal);

    /**
     * 查询放弃总数
     * @param accountId
     * @param childId
     * @param isDeal
     * @return
     */
    int selectdealF(@Param("accountId")int accountId,@Param("childId")int childId,@Param("isDeal")int isDeal);

    /**
     * 筛选总数
     * @param accountId
     * @param childId
     * @param xxly
     * @param ycsj
     * @param px
     * @param isDeal
     * @return
     */
    int selectScreenCount(@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("ycsj")String ycsj,@Param("px")int px,@Param("isDeal")int isDeal);

    /**
     * 筛选列表
     * @param p1
     * @param p2
     * @param accountId
     * @param childId
     * @param xxly
     * @param ycsj
     * @param dfzt
     * @param isDeal
     * @return
     */
    List<AcquisitionCar> ScreenMessageAll(@Param("p1") int p1,@Param("p2") int p2,@Param("accountId")int accountId,@Param("childId")int childId,@Param("xxly")String xxly,@Param("ycsj")String ycsj,@Param("px")int dfzt,@Param("isDeal") int isDeal);

    /**
     * 搜索总条数
     * @param selects
     * @param accountId
     * @param childId
     * @return
     */
    int selectBySearchKey(@Param("selects")String selects,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 搜索列表
     * @param p1
     * @param p2
     * @param selects
     * @param accountId
     * @param childId
     * @return
     */
    List<AcquisitionCar> queryBysearchkey(@Param("p1")int p1, @Param("p2")int p2,@Param("selects")String selects,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 根据ID查询信息
     * @param id
     * @return
     */
    AcquisitionCar selectMsgById(@Param("id") int id);

    /**
     * 根据AcquisitionID查询协商价格信息
     * @param id
     * @return
     */
    List<AcquisitionConsult> selectConsultById(@Param("id") int id);

    /**
     * 查询alias和客户电话
     * @return
     */
    List<AcquisitionCar> selectAliasCustName();

    int selectByIdCount(@Param("accountId") int accountId, @Param("childId")int childId,@Param("otherId")int otherId);

    List<AcquisitionCar> selectById(@Param("p1")int p1, @Param("p2")int p2,@Param("accountId")int accountId,@Param("childId")int childId,@Param("otherId")int otherId);

    Integer selectIsdeal(int id);
    int insertSxyWb(AcquisitionCar acquisitionCar);

    String findNameById(int id);

    //根据主/子账号查出电话

    String findByChildMobileId(int id);

    String findByAcountMobileId(int id);

    Integer compareWBId(int id);

    List<AcquisitionCarPricing> selectPricingInfo(@Param("id")int id);

    int deletePricingInfo(@Param("acquisitionId") int acquisitionId);

    int insertPricingInfo(AcquisitionCarPricing acquisitionCarPricing);

    Number selectNumber(@Param("type")String type, @Param("selects")String selects, @Param("months")String months, @Param("xxly")String xxly, @Param("px")String px, @Param("cjzt")String cjzt, @Param("accountId")int accountId, @Param("childId")int childId);

    List<AcquisitionCar> querySelectList(@Param("p1")int p1, @Param("p2")int p2, @Param("type")String type, @Param("selects")String selects, @Param("months")String months, @Param("xxly")String xxly, @Param("px")String px, @Param("cjzt")String cjzt, @Param("accountId")int accountId, @Param("childId")int childId,@Param("flage")int flage);

    int selectDay(@Param("accountId")int accountId, @Param("childId")int childId,@Param("flage")int flage);

    int selectWeek(@Param("accountId")int accountId, @Param("childId")int childId,@Param("flage")int flage);

    int selectMonth(@Param("accountId")int accountId, @Param("childId")int childId,@Param("flage")int flage);

    /**
     * 查询出子账号手车身分(是收车主管还是收车员)
     */
    List<CollectIdentityResponse> selectSCSF(@Param("childId")int childId);

    /**
    * @author zhangD
    * @date 2018/2/28 11:23
    * @Description: 通过tid查询合同
    */
   String selectContract(Integer id);
}
