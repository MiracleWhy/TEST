package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.ChildInfoResponse;
import com.uton.carsokApi.controller.response.InfoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */
public interface ChildInfoMapper {
    /**
     * 子帐号我的
     * @param id
     * @return
     */
    ChildInfoResponse selectChildInfo(@Param("id") int id);

    List<InfoResponse> selectInfoByCustPhone(@Param("mobile") String mobile);

    InfoResponse selectInfoByCarId(@Param("carId") String carId);
}
