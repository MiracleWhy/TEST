package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.CarsokAppVersion;

public interface CarsokAppVersionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_app_version
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_app_version
     *
     * @mbggenerated
     */
    int insert(CarsokAppVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_app_version
     *
     * @mbggenerated
     */
    int insertSelective(CarsokAppVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_app_version
     *
     * @mbggenerated
     */
    CarsokAppVersion selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_app_version
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CarsokAppVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carsok_app_version
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CarsokAppVersion record);

    CarsokAppVersion selectRecentInfo(String distinction);
}