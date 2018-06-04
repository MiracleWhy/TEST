package com.uton.carsokApi.service.impl;

import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.AllyRequest;
import com.uton.carsokApi.controller.response.AllyResponse;
import com.uton.carsokApi.controller.response.ContentPushResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.AllyMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.AllyService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Raytine on 2017/10/18.
 */
@Service
@Transactional
public class AllyServiceImpl implements AllyService {

    private final static Logger logger = Logger.getLogger(AllyServiceImpl.class);

    @Autowired
    AllyMapper allyMapper;

    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    AcountMapper acountMapper;

    /**
     * 通过商家编号查询新的盟友信息列表
     * @param accountCode
     * @return
     */
    @Override
    public List<AllyResponse> getNewFriendList(String accountCode) {
        return allyMapper.getNewFriendList(accountCode);
    }

    /**
     * 车商联盟首页好友列表展示查询
     * @param accountCode
     * @return
     */
    @Override
    public List<AllyResponse> getFriendList(String accountCode) {
        return allyMapper.getFriendList(accountCode);
    }

    /**
     * 推荐好友列表（按照登录人所在地域推荐）
     * @param accountCode
     * @return
     */
    @Override
    public List<AllyResponse> getLocalFriendList(String accountCode) {
        return allyMapper.getLocalFriendList(accountCode);
    }

    /**
     *按条件搜索好友查询
     * @param searchName
     * @return
     */
    @Override
    public List<AllyResponse> searchFriendList(String searchName,String accountCode) {
        return allyMapper.searchFriendList(searchName,accountCode);
    }

    /**
     * 新增好友关系
     * @param allyRequest
     * @return
     */
    @Override
    public int addNewFriend(AllyRequest allyRequest) {
        int reFlag = 0;
        //插入消息列表数据参数
        ContentPushResponse contentPushResponse = new ContentPushResponse();
        contentPushResponse.setCreateTime(new Date());
        contentPushResponse.setContentType("allyMessage");
        AllyResponse allyResponse = new AllyResponse();
        if(StringUtil.isEmpty(StringUtil.nvl(allyRequest.getId()))){
            allyResponse  = allyMapper.getRelation(allyRequest);
        }
        if(allyResponse != null || !StringUtil.isEmpty(StringUtil.nvl(allyRequest.getId()))){//不为空，存在好友关系，更新现有关系
            if(!StringUtil.isEmpty(StringUtil.nvl(allyRequest.getId()))){
                reFlag = allyMapper.updateFriendRelation(allyRequest);
            }else{
                allyRequest.setId(allyResponse.getId());
                reFlag = allyMapper.updateFriendRelation(allyRequest);
            }

            //申请状态为2，通过申请，在消息表中给自己发送申请通过通知，并给要添加的好友发送已接收申请通知
            if(2 == allyRequest.getRelationStatus()){
                contentPushResponse.setPushTo(allyRequest.getAccountCode());
                contentPushResponse.setTitle("申请通过");
                contentPushResponse.setContent(allyMapper.getMerchantName(allyRequest.getFriendAccount())+"已接受了您的联盟申请！");
                allyMapper.addMessage(contentPushResponse);
                contentPushResponse.setPushTo(allyRequest.getFriendAccount());
                contentPushResponse.setTitle("已接受申请");
                contentPushResponse.setContent("您已接受了"+allyMapper.getMerchantName(allyRequest.getAccountCode())+"的联盟申请！");
                allyMapper.addMessage(contentPushResponse);
            }
            //申请状态为3，拒绝申请，在消息表中给要添加的好友发送好友拒绝通知
            if(3 == allyRequest.getRelationStatus()){
                allyResponse = allyMapper.getRelation(allyRequest);
                contentPushResponse.setPushTo(allyResponse.getAccountCode());
                contentPushResponse.setContent(allyMapper.getMerchantName(allyResponse.getFriendAccount()) + "：" +allyRequest.getApplyMessage());
                contentPushResponse.setTitle("已拒绝申请");
                allyMapper.addMessage(contentPushResponse);
            }
        }else{//为空，不存在好友关系，新增
            allyRequest.setCreateTime(new Date());
            reFlag = allyMapper.addNewFriend(allyRequest);
            //申请状态为1，在消息表中给要添加的好友发送好友申请通知
            if(1 == allyRequest.getRelationStatus()){
                contentPushResponse.setPushTo(allyRequest.getFriendAccount());
                contentPushResponse.setTitle("申请通知");
                contentPushResponse.setContent(allyMapper.getMerchantName(allyRequest.getAccountCode()) + "：" +allyRequest.getApplyMessage());
                allyMapper.addMessage(contentPushResponse);
            }
        }
        return reFlag;
    }


    /**
     *  获取消息列表
     * @param accountCode
     * @return
     */
    @Override
    public List<ContentPushResponse> getMessageList(String accountCode) {
        List<ContentPushResponse> messageList = allyMapper.getMessageList(accountCode);
        for(int i = 0; i < messageList.size(); i++){
            allyMapper.updateMessageStatusById(messageList.get(i).getId());
        }
        return messageList;
    }

    /**
     * 根据id删除消息（逻辑删除）
     * @param id
     * @return
     */
    @Override
    public int updateMessageById(String id) {
        return allyMapper.updateMessageById(id);
    }


    /**
     *消息通知，查询是否有未读消息
     * @param accountCode
     * @return
     */
    @Override
    public int getMessageCount(String accountCode) {
        return allyMapper.getMessageCount(accountCode);
    }

    /**
     *查询是否有新的好友申请
     * @param accountCode
     * @return
     */
    @Override
    public int getNewFriendCount(String accountCode) {
        return allyMapper.getNewFriendCount(accountCode);
    }

    /**
     * 获取账号信息
     * @param token
     * @param subToken
     * @return
     */
    @Override
    public Acount getMainAccount(String token, String subToken) {
            try {
                //token 不等于空 说明是主账户登录
                if (!StringUtils.isEmpty(token)) {
                    ValueOperations<String, Acount> valueOperations=redisTemplate.opsForValue();
                    Acount acount =valueOperations.get(token);
                    return acount;
                }
                //subToken 不为空 说明是子账户登录
                if (!StringUtils.isEmpty(subToken)) {
                    ValueOperations<String, ChildAccount> valueOperations=redisTemplate.opsForValue();
                    try{
                        ChildAccount childAcount =valueOperations.get(subToken);

                        //通过子账户里面的手机号码 查询主账户的账户信息返回
                        Acount queryAcount = new Acount();
                        queryAcount.setAccount(childAcount.getAccountPhone());
                        Acount acount = acountMapper.selectByModel(queryAcount);
                        acount.setSubPhone(childAcount.getChildAccountMobile());
                        return acount;
                    }catch (NullPointerException e){
                        throw new NullPointerException("缓存异常请重新登录");
                    }
                }
                logger.error("缓存中获取主用户信息异常, token = " + token);
                logger.error("缓存中获取主用户信息异常, subToken = " + subToken);
                return null;
            } catch (Exception e) {
                logger.error("缓存中获取主用户信息异常, token = " + token, e);
                logger.error("缓存中获取主用户信息异常, subToken = " +subToken, e);
                throw e;
            }
    }

    /**
     * 按条件分页搜索好友查询
     * @param searchName
     * @param accountCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<AllyResponse> searchFriendListV1(String searchName, String accountCode, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AllyResponse> list = allyMapper.searchFriendList(searchName,accountCode);
        return list;
    }





}
