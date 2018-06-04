package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.CarsokLoginToken;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */
public interface CarsokLoginMapper {
    int insert(@Param("account") String account, @Param("token") String token, @Param("loginTime") Date loginTime,@Param("ipAddress") String ipAddress,@Param("addressMsg") String addressMsg);
    int update(@Param("token") String token, @Param("loginOutTime") Date loginOutTime);
    List<CarsokLoginToken> selectAllLoginToken(@Param("accounts") String accounts);
}
