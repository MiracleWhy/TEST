package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.ChildAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by SEELE on 2017/11/9.
 */
public interface SaasAuthorityMapper {
    List<String> getAuthorityByUserId( @Param("childId")Integer childId);

    List<ChildAccount> queryPrivilegedChildAccount(@Param("accountPhone")String accountPhone, @Param("permissionNames") List<String> permissionNames);


    List<String> selectQxName(@Param("childPhone") String childPhone);
    //只查子账户
    List<ChildAccount> selectChild(@Param("accountPhone")String accountPhone ,@Param("powerName") List<String> powerName);
}
