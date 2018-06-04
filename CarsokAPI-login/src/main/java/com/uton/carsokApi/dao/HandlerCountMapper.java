package com.uton.carsokApi.dao;


import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/6/14.
 */
public interface HandlerCountMapper {
    /**
     * 查询门店待办数
     * @param mobile
     * @return
     */
    int selectMenDianCount(@Param("mobile") String mobile);
    /**
     * 查询保有待办数
     * @param mobile
     * @return
     */
    int selectBaoYouCount(@Param("mobile") String mobile);
    /**
     * 查询日检待办数
     * @param mobile
     * @return
     */
    int selectRiJianCount(@Param("mobile") String mobile);
    /**
     * 查询收车待办数
     * @param mobile
     * @return
     */
    int selectShouCheCount(@Param("mobile") String mobile);

    /**
     * 查询喜报待办数
     * @param mobile
     * @return
     */
    int selectXiBaoCount(@Param("mobile") String mobile);

    /**
     * 删除门店消息
     * @param mendianId
     * @return
     */
    int deleteMendianMsg(@Param("mendianId") int mendianId);

    /**
     * 删除保有消息
     * @param baoyouId
     * @return
     */
    int deleteBaoyouMsg(@Param("baoyouId") int baoyouId);

    /**
     * 删除保有消息
     * @param shoucheId
     * @return
     */
    int deleteShoucheMsg(@Param("shoucheId") int shoucheId);
}
