package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.dao.UserSignMapper;
import com.uton.carsokApi.model.UserSign;
import com.uton.carsokApi.service.UserSignService;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.HttpClientUtil;
import com.uton.carsokApi.util.HttpUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by SEELE on 2017/10/20.
 */
@Service
public class UserSignServiceImpl implements UserSignService {

    @Autowired
    UserSignMapper userSignMapper;


    @Value("${bolang}")
    String bolangBaseUrl;


    private final static Logger logger = Logger.getLogger(UserSignServiceImpl.class);

/*    public boolean doUserSign(String accountId,String mobile)
    {

    }*/

    /***
     * 签到
     * @param accountId
     * @return
     */
    public int updateUserSign(String accountId) {
        int result = 0;
        try {
            UserSign userSign = userSignMapper.selectUserSignByAccountId(accountId);
            //数据库中无记录
            if (userSign == null) {
                userSign = new UserSign();
                userSign.setAccountId(accountId);
                userSign.setContinuityTimes(1);
                userSign.setEnable(1);
                userSign.setLastSignDate(new Date());
                userSign.setPresentTimes(0);
                userSign.setCreateTime(new Date());
                userSign.setUpdateTime(new Date());

                userSignMapper.insert(userSign);
            }
            //数据库有记录并且今日未签到
            else if (DateUtil.getIntervalDays(userSign.getLastSignDate(), new Date()) > 0) {
                userSignMapper.updateUserSign(accountId);
                userSign = userSignMapper.selectUserSignByAccountId(accountId);
                result = userSign.getContinuityTimes();
            } else {
                //do nothing
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = 0;
        }
        return result;
    }

    public boolean updatePresentTimes(String accountId, int times) {
        boolean result = true;
        try {

            userSignMapper.updatePresentTimes(accountId, times);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = false;
        }
        return result;
    }


    /***
     * 获取连续签到次数
     * @param accountId
     * @return
     */
    public int getSignTimes(String accountId) {
        int signTimes = 0;
        try {
            signTimes = userSignMapper.selectUserContinuitySign(accountId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return signTimes;
    }

    /***
     * 赠送维保次数（调用博朗）
     * @param mobile
     * @param times
     * @return
     */
    public boolean presentToBolang(String mobile, int times) {
        boolean result = false;
        try {
            Map<String, String> params = new HashedMap();
            params.put("phoneNumber", mobile);
            //arams.put("id", mobile);
            params.put("count", String.valueOf(times));

            logger.info(String.format("开始申请免费博朗次数，账号%s,次数%s", mobile, times));

            String responseStr = HttpClientUtil.sendPostRequest(bolangBaseUrl + "/addFreeCount", params, null, null);

            logger.info("博朗次数申请完成:" + responseStr);

            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public UserSign getUserSign(String accountId)
    {
        return userSignMapper.selectUserSignByAccountId(accountId);
    }



}
