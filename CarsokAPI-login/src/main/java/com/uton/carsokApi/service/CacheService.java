package com.uton.carsokApi.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;

/**
 * 公共缓存相关
 * @author bing.cheng
 *
 */
@Service("cacheService")
public class CacheService {
	
	private final static Logger logger = Logger.getLogger(CacheService.class);

	@Resource
    RedisTemplate redisTemplate;
	
	@Autowired 
	AcountMapper acountMapper;
	
	/***
	 * 缓存中获取主用户信息
	 * 通过token获取
	 * @param req
	 */
	public Acount getAcountInfoFromCache(HttpServletRequest req){
		try {
			//token 不等于空 说明是主账户登录
			String token = req.getHeader("token");
			if (!StringUtils.isEmpty(token)) {
				ValueOperations<String, Acount> valueOperations=redisTemplate.opsForValue();
				Acount acount =valueOperations.get(token);
				if(acount == null){
					logger.error("1、缓存中获取主用户信息异常, token = " + req.getHeader("token"));
					throw new NullPointerException("缓存异常请重新登录");
				}
				return acount;
			}

			//subToken 不为空 说明是子账户登录 
			String subToken = req.getHeader("subToken");
			if (!StringUtils.isEmpty(subToken)) {
				ValueOperations<String, ChildAccount> valueOperations=redisTemplate.opsForValue();
				try{
					ChildAccount childAcount =valueOperations.get(subToken);
					if(childAcount == null){
						logger.error("4、缓存中获取子用户信息异常, subToken = " + req.getHeader("subToken"));
						throw new NullPointerException("缓存异常请重新登录");
					}
					//通过子账户里面的手机号码 查询主账户的账户信息返回
					Acount queryAcount = new Acount();
					queryAcount.setAccount(childAcount.getAccountPhone());
					Acount acount = acountMapper.selectByModel(queryAcount);
					if(acount == null){
						logger.error("2、缓存中获取子用户信息异常, subToken = " + req.getHeader("subToken"));
						throw new NullPointerException("缓存异常请重新登录");
					}
					acount.setSubPhone(childAcount.getChildAccountMobile());
					return acount;
				}catch (NullPointerException e){
					throw new NullPointerException("缓存异常请重新登录");
				}
			}
			logger.error("缓存中获取主、子用户信息异常, token = " + req.getHeader("token") + ",subToken = " + req.getHeader("subToken"));
			throw new NullPointerException("缓存异常请重新登录");
//			return null;
		} catch (Exception e) {
			logger.error("3、缓存中获取主用户信息异常, token = " + req.getHeader("token"), e);
			logger.error("3、缓存中获取子用子信息异常, subToken = " + req.getHeader("subToken"), e);
			throw e;
		}
	}
	
	/***
	 * 缓存中子账户信息
	 * 通过subToken获取
	 * @param req
	 */
	public ChildAccount getSubAcountInfoFromCache(HttpServletRequest req){
		try {
			String token = req.getHeader("subToken");
			ValueOperations<String, ChildAccount> valueOperations=redisTemplate.opsForValue();
			ChildAccount acount =valueOperations.get(token);
			return acount;
		} catch (Exception e) {
			logger.error("缓存中获取子用户信息异常, token = " + req.getHeader("subToken"), e);
			throw e;
		}
	}
	
	/**
	 * 缓存中验证码校验
	 * @param account
	 * @param code
	 */
	public boolean checkCode(String account, String code) {
		 ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
	     String cacheCode =valueOperations.get(account);
	     if (code.equals(cacheCode)) {
	    	 return true;
	     }
	     return false;
	}
}
