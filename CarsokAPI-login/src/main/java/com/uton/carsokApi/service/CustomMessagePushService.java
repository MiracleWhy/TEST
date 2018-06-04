package com.uton.carsokApi.service;

import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.model.Acount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@Service
public class CustomMessagePushService {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    CacheService cacheService;

    @Autowired
    AcountMapper accountMapper;

    public List<String> selectAll(){
        try{
            List<String> account = accountMapper.selectAllAccount();
            return account;
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }

    public int updateQuotientScoreByMerchant(HttpServletRequest request,int id){
        Acount record = new Acount();
        record.setId(id);
        Acount acount = accountMapper.selectByModel(record);
        Acount acounts = new Acount();
        acounts.setId(id);
        acounts.setQuotientScore(acount.getQuotientScore()+(double)20);
        return accountMapper.updateQuotientScore(acounts);
    }
}
