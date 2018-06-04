package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.CarForgSale;
import com.uton.carsokApi.model.CarForgSaleOne;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.SalesForg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8/008.
 */
public interface CarForgSaleMapper {

    List childId(@Param("id") int id);

    List<ChildAccount> selectChildId(@Param("phone") String phone);

    Integer selectOneChild(@Param("phone") String phone);

    /**
     * 统计某时间段售车数
     */
    Integer countSaledCar(@Param("accountId")Integer accountId,@Param("childId")Integer childId,@Param("startDate")String startDate,@Param("endDate")String endDate);

    Integer countSaledCarBefore(@Param("accountId")Integer accountId,@Param("childId")Integer childId,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("type")Integer type);
}
