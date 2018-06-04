package com.uton.carsokApi.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.IdcardverifyRequest;
import com.uton.carsokApi.controller.request.WechatVerifyRequest;
import com.uton.carsokApi.controller.response.IsIdCardverifyCardResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.model.Acount;

/**
 * 认证相关
 * 
 * @author bing.cheng
 *
 */
@Service
public class AuthenticationService {

	private final static Logger logger = Logger.getLogger(AuthenticationService.class);

	@Resource
	RedisTemplate redisTemplate;

	@Autowired
	JisuAuthService jisuAuthService;
	
	@Resource
	CacheService cacheService;
	
	@Autowired 
	AcountMapper acountMapper;

	public BaseResult idcardverify(HttpServletRequest request, IdcardverifyRequest vo) {
		logger.info("idcardverify 请求参数：" + JSON.toJSONString(vo));
		boolean flag = jisuAuthService.idcardverify(vo.getIdcard(), vo.getRealName());
		if (!flag) {
			return BaseResult.fail(ErrorCode.IdVerifyFail, ErrorCode.IdVerifyFailInfo);
		}

		// 更新用户表 
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount upAcount = new Acount();
		upAcount.setId(acount.getId());
		upAcount.setRealName(vo.getRealName());
		upAcount.setIdcard(vo.getIdcard());
		upAcount.setIsRealNameAudit((short) 2);
		upAcount.setUpdateTime(new Date());
		upAcount.setToken(acount.getToken());
		acount.setQuotientScore(acount.getQuotientScore()+20);
		acountMapper.updateQuotientScore(acount);
		acountMapper.updateBySelective(upAcount);
		//redisTemplate.opsForValue().set(acount.getToken(), acount, 30, TimeUnit.DAYS);
		return BaseResult.success();
	}

	/**
	 * 是否进行了实名认证
	 * @param request
	 * @return
	 */
	public BaseResult isIdcardverify(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		if(acountRes == null){
			return BaseResult.fail("-8","还未登录");
		}
		IsIdCardverifyCardResponse res = new IsIdCardverifyCardResponse();
		res.setIsVerify(acountRes.getIsRealNameAudit());
		return BaseResult.success(res);
	}
	public BaseResult wechatVerify(HttpServletRequest request, WechatVerifyRequest vo) throws UnsupportedEncodingException {
		logger.info("wechatVerify 请求参数：" + JSON.toJSONString(vo));
		// 更新用户表 
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acount1 = acountMapper.selectByModel(acount);
		if(acount.getIsRealNameAudit()==null||acount.getIsRealNameAudit()!=3){
			acount1.setQuotientScore(acount.getQuotientScore()+(double)20);
			acount1.setIsRealNameAudit((short)3);
		}
		List<Acount> account=acountMapper.selectByOpenId(vo.getOpenId());
		boolean flag=true;
		if(account.size()>0){
			flag=false;
			for(Acount item:account){
				if(acount.getId().compareTo(item.getId())==0){
					flag=true;
				}
			}
		}
		if(flag==false){
			return BaseResult.fail(ErrorCode.WechatVerifyFail, ErrorCode.WechatVerifyFailInfo);
		}
		acount1.setWxHeadUrlString(vo.getWxHeadUrlString());
		acount1.setWxNickName(vo.getWxNickName()==null?"".getBytes("UTF-8"):vo.getWxNickName().getBytes("UTF-8"));
		acount1.setRefreshToken(vo.getRefreshToken());
		acount1.setOpenId(vo.getOpenId());
		acount1.setUpdateTime(new Date());
		acountMapper.updateBySelective(acount1);
		acount.setQuotientScore(acount.getQuotientScore()+(double)20);
//		redisTemplate.opsForValue().set(acount.getToken(), acount, 30, TimeUnit.DAYS);
		return BaseResult.success();
	}
}
