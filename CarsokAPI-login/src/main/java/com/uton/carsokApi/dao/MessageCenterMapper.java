package com.uton.carsokApi.dao;

import com.uton.carsokApi.model.CarsokZbTaskSxyWb;
import com.uton.carsokApi.model.MessageCenter;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public interface MessageCenterMapper {
    /**
     * 添加信息
     * @param messageCenter
     * @return
     */
    int messageCenterAdd(MessageCenter messageCenter);

    /**
     * 根据消息类型查询
     * @param contentTypes
     * @return
     */
    List<MessageCenter> selectByContentType(@Param("pushTo")String pushTo,@Param("contentTypes") List<String> contentTypes,@Param("createTime")Date createTime);

    List<MessageCenter> selectByContentTypeOfZjl(@Param("mobiles")List<String> mobiles,@Param("contentTypes") List<String> contentTypes,@Param("createTime")Date createTime);
    /**
     * 通过taskId和roleName删除消息
     * @param taskId
     * @param roleName
     * @return
     */
    int deleteCenter(@Param("taskId")int taskId,@Param("roleName")String roleName);

    int deleteCenterBytaskId(@Param("taskId")int taskId);

    int deleteMessage(@Param("pushTo")String pushTo,@Param("contentType")String contentType);

    int selectCountByTime(@Param("mobile")String mobile,@Param("time")Date time,@Param("contentTypes") List<String> contentTypes);

    /**
     * 查看待办总数
     * @param mobile
     * @param roleList
     * @return
     */
    int selectHandleCount(@Param("mobile")String mobile,@Param("roleList")List<String> roleList);

    /**
     * 总经理查看待办总数
     * @param mobiles
     * @param roleList
     * @return
     */
    int selectHandleCountByZjl(@Param("mobiles")List<String> mobiles,@Param("roleList")List<String> roleList);

    /**
     * 手续员推送
     * @param id
     * @return
     */
    CarsokZbTaskSxyWb selectsxyts(int id);

    Integer selectTidId(int taskId);

    int deleteCenterSXY(@Param("id")int id,@Param("roleName")String roleName);

    int updatePushStatusById(@Param("id")int id,@Param("pushStatus")int pushStatus);

    String selectByProductId(@Param("Pid") Integer Pid);
}
