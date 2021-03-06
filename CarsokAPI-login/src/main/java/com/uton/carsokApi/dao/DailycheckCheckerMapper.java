package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.DailyCheckerResponse;
import com.uton.carsokApi.controller.response.DailycheckCheckerTaskResponse;
import com.uton.carsokApi.model.DailycheckChecker;

import java.util.List;

public interface DailycheckCheckerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_checker
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_checker
     *
     * @mbggenerated
     */
    int insert(DailycheckChecker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_checker
     *
     * @mbggenerated
     */
    int insertSelective(DailycheckChecker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_checker
     *
     * @mbggenerated
     */
    DailycheckChecker selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_checker
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DailycheckChecker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_dailycheck_checker
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DailycheckChecker record);


    List<DailycheckChecker> selectCheckerListByAccount(String accountId);

    int deleteByAccount(String accountId);

    List<DailyCheckerResponse> getCheckerInfoListByAccount(String accountId);

    List<DailycheckCheckerTaskResponse> selectCheckerForTask();
}