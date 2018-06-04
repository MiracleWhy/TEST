package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.AllyRequest;
import com.uton.carsokApi.controller.response.AllyResponse;
import com.uton.carsokApi.controller.response.ContentPushResponse;
import com.uton.carsokApi.model.Acount;

import java.util.List;

/**
 * Created by Raytine on 2017/10/18.
 */
public interface AllyService {

    /**
     * 通过商家编号查询新的盟友信息列表
     * @param accountCode
     * @return
     */
    List<AllyResponse> getNewFriendList(String accountCode);

    /**
     * 车商联盟首页好友列表展示查询
     * @param accountCode
     * @return
     */
    List<AllyResponse> getFriendList(String accountCode);

    /**
     * 推荐好友列表（按照登录人所在地域推荐）
     * @param accountCode
     * @return
     */
    List<AllyResponse> getLocalFriendList(String accountCode);

    /**
     * 按条件搜索好友查询
     * @param searchName
     * @return
     */
    List<AllyResponse> searchFriendList(String searchName,String accountCode);

    /**
     * 新增好友关系
     * @param allyRequest
     * @return
     */
    int addNewFriend(AllyRequest allyRequest);

    /**
     * 获取消息列表
     * @param accountCode
     * @return
     */
    List<ContentPushResponse> getMessageList(String accountCode);


    /**
     * 根据id删除消息（逻辑删除）
     * @param id
     * @return
     */
    int updateMessageById(String id);

    /**
     *消息通知，查询是否有未读消息
     * @param accountCode
     * @return
     */
    int getMessageCount(String accountCode);

    /**
     * 查询是否有新的好友申请
     * @param accountCode
     * @return
     */
    int getNewFriendCount(String accountCode);

    /**
     * 获取账号信息
     * @param token
     * @param subToken
     * @return
     */
    Acount getMainAccount(String token,String subToken);


    /**
     * 按条件分页搜索好友查询
     * @param searchName
     * @return
     */
    List<AllyResponse> searchFriendListV1(String searchName,String accountCode,Integer pageNum,Integer pageSize);
}
