package com.uton.carsokApi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.easemob.server.example.api.IMUserAPI;
import com.easemob.server.example.comm.ClientContext;
import com.easemob.server.example.comm.EasemobRestAPIFactory;
import com.easemob.server.example.comm.body.IMUserBody;
import com.easemob.server.example.comm.body.ResetPasswordBody;
import com.easemob.server.example.comm.wrapper.BodyWrapper;
import com.uton.carsokApi.constants.Common;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.CheckMobileExist;
import com.uton.carsokApi.dao.CarsokLoginMapper;
import com.uton.carsokApi.model.CarsokLoginToken;
import com.uton.carsokApi.service.impl.CarsokLoginServiceImpl;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.controller.request.AddSubUserRequest;
import com.uton.carsokApi.controller.request.DelSubUserRequest;
import com.uton.carsokApi.controller.request.SubLoginRequest;
import com.uton.carsokApi.controller.request.ToDoTaskRequest;
import com.uton.carsokApi.controller.request.UpSubAcountKeyRequest;
import com.uton.carsokApi.controller.request.UpdateSubUserRequest;
import com.uton.carsokApi.controller.response.SubLoginResponse;
import com.uton.carsokApi.controller.response.SubUserListResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.ZbRoleMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.ChildAccountCache;
import com.uton.carsokApi.util.StringUtil;

/**
 * 子账户service
 * @author bing.cheng
 *
 */
@Service
public class SubUserService {
	
	private final static Logger logger = Logger.getLogger(SubUserService.class);
	
	@Autowired
	ChildAccountMapper childAccountMapper;
	
	@Autowired 
	AcountMapper acountMapper;
	
	@Autowired
	ZbRoleMapper zbroleMapper;

	@Resource
	RedisTemplate redisTemplate;
	
	@Resource
	CacheService cacheService;

	@Resource
	LoginService loginService;

	@Resource
	ICarsokLoginService iCarsokLoginService;

	@Autowired
	CarsokLoginMapper carsokLoginMapper;

	/**
	 * 子账户登录
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult subLogin(HttpServletRequest request, SubLoginRequest vo) {
		Acount record = new Acount();
		record.setAccount(vo.getAccount());
		record.setAccountKey(vo.getAccountKey());
		Acount acount = acountMapper.selectByModel(record);
		if (null == acount) {
			return BaseResult.fail(UserErrorCode.UserNotExistErrorRetCode, UserErrorCode.UserNotExistErrorRetInfo);
		}
		
		ChildAccount query = new ChildAccount();
		query.setAccountPhone(vo.getAccount());
		query.setChildAccountMobile(vo.getChildAccountMobile());
		ChildAccount queryRes = childAccountMapper.selectByModel(query);
		if (null == queryRes) {
			return BaseResult.fail(UserErrorCode.UserNotExistErrorRetCode, UserErrorCode.UserNotExistErrorRetInfo);
		}
		List<String> rolelist = zbroleMapper.selectBychildid(queryRes.getId());
		String subToken = UUID.randomUUID().toString();
		//登录成功信息存缓存 
		try {
			redisTemplate.opsForValue().set(subToken, queryRes, 7, TimeUnit.DAYS);
			RequestAuthority authority = (RequestAuthority) redisTemplate.opsForValue().get(Common.USER + queryRes.getAlias());
			if (authority == null) {
				authority = new RequestAuthority();
			}
			authority.setToken(subToken);
			redisTemplate.opsForValue().set(Common.USER + queryRes.getAlias(), authority);
			logger.info("子账户登录, 用主帐号为： " + vo.getAccount() + "，密钥为：" + vo.getAccountKey() + "子帐号为：" + vo.getChildAccountMobile() + "，token为：" + subToken );
		} catch (Exception e) {
			logger.error("子账户登录token缓存异常, 用户信息为： " + JSON.toJSONString(acount), e);
			return BaseResult.fail("-1", "子账户登录token缓存异常");
		}
		//友盟alias通过客户端上报，存储到子账户中
		if(!StringUtil.isEmpty(vo.getAlias())){
			ChildAccount upAccount = new ChildAccount();
			upAccount.setId(queryRes.getId());
			upAccount.setAlias(vo.getAlias());
			childAccountMapper.updateByPrimaryKeySelective(upAccount);
		}
		SubLoginResponse res = new SubLoginResponse();
		res.setSubToken(subToken);
		res.setRoles(rolelist);
		res.setChildAccount(queryRes);
		//同时注册环信
		ClientContext clientContext = ClientContext.getInstance();
		clientContext=clientContext.init(ClientContext.INIT_FROM_PROPERTIES);
		EasemobRestAPIFactory factory = clientContext.getAPIFactory();
		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		BodyWrapper userBody = new IMUserBody(vo.getChildAccountMobile(), vo.getAccountKey(), vo.getChildAccountMobile());
		user.createNewIMUserSingle(userBody);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		logger.info("子帐号登录token："+subToken+"，登录时间："+sdf.format(new Date()));
		String ip = iCarsokLoginService.getIp(request);
		CarsokLoginServiceImpl.GetAddress address = new CarsokLoginServiceImpl().new GetAddress(vo.getChildAccountMobile(),subToken,ip);
		new Thread(address).start();
		//address.run();
		return BaseResult.success(res);
	}
	

	/**
	 * 新增子账户
	 * @param
	 * @param vo
	 * @return
	 */
	public BaseResult addSubUser(HttpServletRequest req, AddSubUserRequest vo) {
		if(childAccountMapper.selectBymobile(vo.getChildAccountMobile())!=null&&childAccountMapper.selectBymobile(vo.getChildAccountMobile()).size()>0){
			logger.info("重复子帐号："+vo.getChildAccountMobile()+"，已经出现："+childAccountMapper.selectBymobile(vo.getChildAccountMobile()).size()+"次");
			return BaseResult.success();
		}
		Acount acount = cacheService.getAcountInfoFromCache(req);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		ChildAccount record = new ChildAccount();
		record.setAccountPhone(acountRes.getAccount());
		record.setChildAccountMobile(vo.getChildAccountMobile());
		record.setChildAccountName(vo.getChildAccountName());
		Date now = new Date();
		record.setCreateTime(now);
		record.setUpdateTime(now);
		childAccountMapper.insertSelective(record);
		//成功添加子账户车商分+2
		Acount qacount = new Acount();
		qacount.setId(acount.getId());
		Acount selectq = acountMapper.selectQuotientScore(qacount);
		//缓存同步
		acount.setQuotientScore(selectq.getQuotientScore()+2);
		//修改数据库车商分
		Acount updateAcount = new Acount();
		updateAcount.setId(acount.getId());
		updateAcount.setQuotientScore(selectq.getQuotientScore()+2);
		acountMapper.updateQuotientScore(updateAcount);
		//缓存重新赋值
//		redisTemplate.opsForValue().set(acount.getToken(), acount, 30, TimeUnit.DAYS);
		return BaseResult.success();
	}


	/**
	 * 修改子账户
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult upSubUser(HttpServletRequest request, UpdateSubUserRequest vo) {
		ChildAccount record = new ChildAccount();
		record.setChildAccountMobile(vo.getChildAccountMobile());
		record.setChildAccountName(vo.getChildAccountName());
		record.setId(vo.getId());
		record.setUpdateTime(new Date());
		childAccountMapper.updateByPrimaryKeySelective(record);
		return BaseResult.success();
	}


	/**
	 * 删除子账户
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult delSubUser(HttpServletRequest request, DelSubUserRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		ChildAccount child = new ChildAccount();
		child.setId(vo.getId());
		ChildAccount childAcount = childAccountMapper.selectByModel(child);
		childAccountMapper.deleteByPrimaryKey(vo.getId());
		//成功删除子账户车商分-2
		Acount qacount = new Acount();
		qacount.setId(acount.getId());
		Acount selectq = acountMapper.selectQuotientScore(qacount);
		//缓存同步
		acount.setQuotientScore(selectq.getQuotientScore()-2);
		//修改数据库车商分
		Acount updateAcount = new Acount();
		updateAcount.setId(acount.getId());
		updateAcount.setQuotientScore(selectq.getQuotientScore()-2);
		acountMapper.updateQuotientScore(updateAcount);
		if(childAcount != null){
			List<CarsokLoginToken> selectAllLoginToken = carsokLoginMapper.selectAllLoginToken(childAcount.getChildAccountMobile());
			if(selectAllLoginToken != null){
				for(CarsokLoginToken token:selectAllLoginToken){
					redisTemplate.delete(token.getToken());
				}
			}
		}
		//缓存重新赋值
//		redisTemplate.opsForValue().set(acount.getToken(), acount, 30, TimeUnit.DAYS);
		return BaseResult.success();
	}


	/**
	 * 获取子账户列表
	 * @param request
	 * @return
	 */
	public BaseResult subUserList(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		
		ChildAccount record = new ChildAccount();
		record.setAccountPhone(acountRes.getAccount());
		List<ChildAccount> list = childAccountMapper.selectListByModel(record);
		if (null == list || list.size() <= 0) {
			return BaseResult.success();
		}
		
		List<SubUserListResponse> resList = new ArrayList<>();
		for (ChildAccount model : list) {
			SubUserListResponse res = new SubUserListResponse();
			res.setChildAccountMobile(model.getChildAccountMobile());
			res.setChildAccountName(model.getChildAccountName());
			res.setId(model.getId());
			resList.add(res);
		}
		return BaseResult.success(resList);
	}
	
	public BaseResult selectListByRole(HttpServletRequest request,ToDoTaskRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", acountRes.getAccount());
		map.put("roleName", vo.getRoleName());
		List<ChildAccount> list = childAccountMapper.selectListByRole(map);
		if (null == list || list.size() <= 0) {
			return BaseResult.success();
		}
		
		List<SubUserListResponse> resList = new ArrayList<>();
		for (ChildAccount model : list) {
			SubUserListResponse res = new SubUserListResponse();
			res.setChildAccountMobile(model.getChildAccountMobile());
			res.setChildAccountName(model.getChildAccountName());
			res.setId(model.getId());
			resList.add(res);
		}
		return BaseResult.success(resList);
	}
	
	/**
	 * 修改主账号密钥
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult upSubLoginKey(HttpServletRequest request, UpSubAcountKeyRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		Acount acountUp = new Acount();
		acountUp.setId(acountRes.getId());
		acountUp.setUpdateTime(new Date());
		acountUp.setAccountKey(vo.getAccountKey());
		acountMapper.updateBySelective(acountUp);
		List<ChildAccount> childAccountList = childAccountMapper.selectAllChild(acount.getAccount());
//		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
//		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//		BodyWrapper b = new ResetPasswordBody(vo.getAccountKey());
		for(ChildAccount child:childAccountList){
			//user.modifyIMUserPasswordWithAdminToken(child.getChildAccountMobile(),b);
			LoginService login = new LoginService();
			LoginService.UpdateHuanxin updateHuanxin = login.new UpdateHuanxin(acount.getAccount(),vo.getAccountKey());
            new Thread(updateHuanxin).start();
            updateHuanxin.run();
		}
		return BaseResult.success();
	}




	/**
	 *  增加子账号，追加唯一性校验
	 *  @param request
	 *  @param vo
	 */
	public BaseResult subUserQueryOnlyChk(HttpServletRequest request, QuerySubUserOnlyChkRequest vo){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		List<CheckMobileExist> count = acountMapper.querySubUserOnlyChk(vo.getMobile());
		for(int i = 0;i<count.size();i++) {
			if(!count.get(i).getCount().equals("0")){
				if(i==Integer.valueOf(0)){
					return BaseResult.fail(ErrorCode.UserMoblieChkFail, ErrorCode.UserMoblieChkFailInfo);
				}else{
					return BaseResult.fail(ErrorCode.UserMobileChkParentFail, ErrorCode.UserMobileChkParentFailInfo);
				}
			}
		}
		return BaseResult.success();
	}
	

	public List<SubUserListResponse> getSubUserList(int accountId)
	{
		Acount acountQuery = new Acount();
		acountQuery.setId(accountId);
		Acount acountRes = acountMapper.selectByModel(acountQuery);

		ChildAccount record = new ChildAccount();
		record.setAccountPhone(acountRes.getAccount());
		List<ChildAccount> list = childAccountMapper.selectListByModel(record);

		List<SubUserListResponse> resList = new ArrayList<>();
		for (ChildAccount model : list) {
			SubUserListResponse res = new SubUserListResponse();
			res.setChildAccountMobile(model.getChildAccountMobile());
			res.setChildAccountName(model.getChildAccountName());
			res.setId(model.getId());
			resList.add(res);
		}
		return resList;
	}

	
	public List<String> getRoleList(int subUserId)
	{
		return zbroleMapper.selectBychildid(subUserId);
	}

	/**
	 * 获取子账户权限
	 */
	public List<String> querySubUserRoles(Acount acount){
		ChildAccount query = new ChildAccount();
		query.setAccountPhone(acount.getAccount());
		query.setChildAccountMobile(acount.getSubPhone());
		ChildAccount queryRes = childAccountMapper.selectByModel(query);
		if (null == queryRes) {
			return new ArrayList<>();
		}
		return zbroleMapper.selectBychildid(queryRes.getId());
	}

	/**
	 * 获取子账户使用记录
	 */
	public Map<String,Integer> selectRecordsByChildPhone(String accountId,String childPhone)
	{
		Map<String,Integer> resultMap = new HashedMap();
		try{
			resultMap.put("DailyCheck",childAccountMapper.selectRecordInDailycheck(accountId,childPhone));
			resultMap.put("GleefulReport",childAccountMapper.selectRecordInGleeful(accountId,childPhone));
			resultMap.put("SaleCar",childAccountMapper.selectRecordInProduct(accountId,childPhone));
			resultMap.put("Acquisition",childAccountMapper.selectRecordInAcquisition(accountId,childPhone));
			resultMap.put("CustomerManage",childAccountMapper.selectRecordInCustomerManage(accountId,childPhone));
			resultMap.put("Tenure",childAccountMapper.selectRecordInTenure(accountId,childPhone));
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}

		return resultMap;
	}






}
