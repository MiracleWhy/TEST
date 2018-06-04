package com.uton.carsokApi.dao;

import java.util.List;
import java.util.Map;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.*;
import org.apache.ibatis.annotations.Param;

public interface ChildAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ChildAccount record);

    ChildAccount selectByModel(ChildAccount record);
    
    List<ChildAccount> selectListByModel(ChildAccount record);



    int updateByPrimaryKeySelective(ChildAccount record);
    
    List<ChildAccount> selectListByRole(Map<String, String> map);
    
    List<ChildAccount> selectBymobile(String mobile);

    List<ChildAccount> queryAllChildByAccountPhone(String mobile);

    List<ChildAccount> queryAllChildByChildPhone(String mobile);

    List<ChildAccount> selectAllChild(@Param("accountPhone") String accountPhone);

    List<ChildAccount> selectByAcountmobile (String mobile);
    Integer  selectIdBymobile (String mobile);

    Integer selectRecordInDailycheck(@Param("accountId")String accountId,@Param("childPhone")String childPhone);
    Integer selectRecordInGleeful(@Param("accountId")String accountId,@Param("childPhone")String childPhone);
    Integer selectRecordInProduct(@Param("accountId")String accountId,@Param("childPhone")String childPhone);
    Integer selectRecordInAcquisition(@Param("accountId")String accountId,@Param("childPhone")String childPhone);
    Integer selectRecordInCustomerManage(@Param("accountId")String accountId,@Param("childPhone")String childPhone);
    Integer selectRecordInTenure(@Param("accountId")String accountId,@Param("childPhone")String childPhone);
    List<ChildAndSale> AllMassage(@Param("phone")String phone);
    ChildAndSale AccountButler(@Param("phone")String accountPhone);
    ChildAndSales oneMassage(@Param("id") String id);
    int updateMessage(ChildAccount childAccount);
    List<SelectSaleCar> selectSaleCar(@Param("id")String id);
    ChildAccount selectByChildMobile(String mobile);

    /**
     * 返回CarsokChildAccount的查询
     */
    //通过主账号电话查询所有子账号
    List<CarsokChildAccount> selectListByAccountPhone(@Param("accountPhone")String accountPhone);
    //通过子账号电话查询子账号
    CarsokChildAccount selectOneByChildMobile(@Param("childMobile")String childMobile);


}