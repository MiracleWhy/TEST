package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.request.AllyRequest;
import com.uton.carsokApi.controller.response.AllyResponse;
import com.uton.carsokApi.controller.response.ContentPushResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Raytine on 2017/10/18.
 */
public interface AllyMapper {

    /**
     * 通过商家编号查询新的盟友信息列表
     * @param accountCode
     * @return
     */
    List<AllyResponse> getNewFriendList(@Param("accountCode") String accountCode);


    /**
     * 车商联盟首页好友列表展示查询
     * @param accountCode
     * @return
     */
    List<AllyResponse> getFriendList(@Param("accountCode") String accountCode);


    /**
     * 推荐好友列表（按照登录人所在地域推荐）
     * @param accountCode
     * @return
     */
    List<AllyResponse> getLocalFriendList(@Param("accountCode") String accountCode);


    /**
     * 按条件搜索好友查询
     * @param searchName
     * @return
     */
    List<AllyResponse> searchFriendList(@Param("searchName") String searchName,@Param("accountCode") String accountCode);

    /**
     * 新增好友关系
     * @param allyRequest
     * @return
     */
    int addNewFriend(@Param("allyRequest") AllyRequest allyRequest);

    /**
     * 更新好友关系
     * @param allyRequest
     * @return
     */
    int updateFriendRelation(@Param("allyRequest") AllyRequest allyRequest);

    /**
     * 查找是否存在好友关系
     * @param allyRequest
     * @return
     */
    AllyResponse getRelation(AllyRequest allyRequest);

    /**
     * 获取消息列表
     * @param accountCode
     * @return
     */
    List<ContentPushResponse> getMessageList(@Param("accountCode") String accountCode);

    /**
     * 插入消息
     * @param contentPushResponse
     * @return
     */
    int addMessage(ContentPushResponse contentPushResponse);

    /**
     * 根据id删除消息（逻辑删除）
     * @param id
     * @return
     */
    int updateMessageById(@Param("id") String id);

    /**
     * 更新联盟消息已读状态，1为未读，0为已读
     * @param id
     * @return
     */
    int updateMessageStatusById(@Param("id") Integer id);

    /**
     *消息通知，查询是否有未读消息
     * @param accountCode
     * @return
     */
    int getMessageCount(@Param("accountCode") String accountCode);

    /**
     * 查询是否有新的好友申请
     * @param accountCode
     * @return
     */
    int getNewFriendCount(@Param("accountCode") String accountCode);

    /**
     * 获取车商名称
     * @param accountCode
     * @return
     */
    String getMerchantName(@Param("accountCode") String accountCode);


    List<String> getAllyList(@Param("phone")String phone);

}
