package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.DailyCheckCheckerResult;
import com.uton.carsokApi.model.DailyCheckProductInfo;
import com.uton.carsokApi.model.DailycheckResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DailycheckResultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_result
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_result
     *
     * @mbggenerated
     */
    int insert(DailycheckResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_result
     *
     * @mbggenerated
     */
    int insertSelective(DailycheckResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_result
     *
     * @mbggenerated
     */
    DailycheckResult selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_result
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DailycheckResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_result
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DailycheckResult record);


    DailycheckResult getCheckResultByProductId(@Param("productId") String productId, @Param("checkDate") String date);

    List<DailycheckResult> getHistoryResult(@Param("productId") String productId);

    DailyCheckProductInfo getProductInfoByProductId(@Param("productId") String productId);

    List<DailyCheckCheckerResult> getCheckerResult(@Param("accountId")String accountId,@Param("checker")String checker,@Param("checkDate")String checkDate,@Param("hasChecker")boolean hasChecker);

    List<DailyCheckCheckerResult> getResultList(@Param("accountId")String accountId,@Param("checkDate")String checkDate,@Param("isNotAll")boolean isNotAll);

}