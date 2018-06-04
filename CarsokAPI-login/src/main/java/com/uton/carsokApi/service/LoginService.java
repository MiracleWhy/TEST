package com.uton.carsokApi.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.uton.carsokApi.constants.Common;
import com.uton.carsokApi.constants.ErrorCode;
import com.alibaba.fastjson.JSONObject;
import com.easemob.server.example.api.IMUserAPI;
import com.easemob.server.example.comm.ClientContext;
import com.easemob.server.example.comm.EasemobRestAPIFactory;
import com.easemob.server.example.comm.body.IMUserBody;
import com.easemob.server.example.comm.body.ResetPasswordBody;
import com.easemob.server.example.comm.wrapper.BodyWrapper;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.HuanXinResponse;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.response.CheckMobileExist;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.pay.alipay.util.StringUtil;
import com.uton.carsokApi.service.impl.CarsokLoginServiceImpl;
import com.uton.carsokApi.util.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.controller.response.LoginResponse;

/**
 * 登录相关service
 * @author bing.cheng
 *
 */
@Service
public class LoginService {

	private final static Logger logger = Logger.getLogger(LoginService.class);

	@Autowired
	AcountMapper acountMapper;

	@Autowired
	ChildAccountMapper childAccountMapper;

	@Autowired
	MerchantwalletMapper merchantwalletMapper;

	@Resource
	RedisTemplate redisTemplate;

	@Resource
	SmsService smsService;

	@Autowired
	MessageCenterMapper messageCenterMapper;

	@Resource
	CacheService cacheService;

	@Autowired
	NoticeMapper noticeMapper;

	@Autowired
	PushService pushService;

	@Value("${merchant.image.path}")
	private String merchant_image_path;

	@Resource
	ICarsokLoginService iCarsokLoginService;

//	private String huanxinPassword;
//
//	private String huanxinAcount;

	/**
	 * 登录
	 * @param vo
	 * @return
	 */
	public BaseResult login(LoginRequest vo,HttpServletRequest request) {
		logger.info("Login Start Time = "+new Date());
		String password = vo.getAccountPassword();
		Acount record = new Acount();
		record.setAccount(vo.getAccount());
		record.setAccountPassword(password);
		Acount acount = acountMapper.selectByModel(record);

		if (null == acount) {
			return BaseResult.fail(UserErrorCode.UserNotExistErrorRetCode, UserErrorCode.UserNotExistErrorRetInfo);
		}


		//TODO 接口恶意登录 后期维护
		String userToken = UUID.randomUUID().toString();
		Acount records = new Acount();
		records.setAccount(vo.getAccount());
		records.setAccountPassword(vo.getAccountPassword());
		records.setToken(userToken);
		acountMapper.updateToken(records);
		Acount acounts = acountMapper.selectByModel(record);
		//登录成功信息存缓存
		try {
			redisTemplate.opsForValue().set(userToken, acounts, 30, TimeUnit.DAYS);
			RequestAuthority authority = (RequestAuthority) redisTemplate.opsForValue().get(Common.USER+acounts.getAccount());
			if(authority==null)
			{
				authority=new RequestAuthority();
			}
			authority.setToken(userToken);
			redisTemplate.opsForValue().set(Common.USER+acounts.getAccount(),authority);

			logger.info("主账户登录, 用户帐号为： " + vo.getAccount() + "，密码为：" + vo.getAccountPassword() + "，token为：" + userToken );
		} catch (Exception e) {
			logger.error("主账户登录token缓存异常, 用户信息为： " + JSON.toJSONString(acount), e);
			return BaseResult.fail("-1", "账户登录token缓存异常");
		}
		LoginResponse response = new LoginResponse();
		BaseResult baseResult =  BaseResult.success();
		response.setRole("manager");//主账户默认为经理角色
		response.setToken(userToken);
		response.setQuotientScore(acount.getQuotientScore());
		response.setMaxQuotientScore(1000);

		//同时注册环信
//		ClientContext clientContext = ClientContext.getInstance();
//		clientContext=clientContext.init(ClientContext.INIT_FROM_PROPERTIES);
//		EasemobRestAPIFactory factory = clientContext.getAPIFactory();
//		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//		BodyWrapper userBody = new IMUserBody(acount.getAccount(), acount.getAccountPassword(), acount.getAccount());
//		user.createNewIMUserSingle(userBody);
		baseResult.setData(response);

		RegisterHuanxin registerHuanxin = new RegisterHuanxin(acount.getAccount(),vo.getAccountPassword());
		new Thread(registerHuanxin).start();
		//registerHuanxin.run();

		logger.info("Login End Time = " + new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		logger.info("主帐号登录token："+userToken+"，登录时间："+sdf.format(new Date()));
		String ip = iCarsokLoginService.getIp(request);
		CarsokLoginServiceImpl.GetAddress address = new CarsokLoginServiceImpl().new GetAddress(vo.getAccount(),userToken,ip);
		new Thread(address).start();
		//address.run();
		return baseResult;
	}

	/**
	 * 是否已经登录
	 * @param request
	 * @return
	 */
	public BaseResult isLogin(HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		if(acount == null){
			return BaseResult.fail("-8", "还未登录");
		}
		LoginResponse response = new LoginResponse();
		Acount qacount = new Acount();
		qacount.setId(acount.getId());
		qacount.setAccount(acount.getAccount());
		qacount.setAccountCode(acount.getAccountCode());
		qacount.setAccountPassword(acount.getAccountPassword());
		Acount selectq = acountMapper.selectQuotientScore(qacount);
		if(selectq == null){
			return BaseResult.fail("-8", "还未登录");
		}
		double quotientScore = selectq.getQuotientScore();
		if(acount!=null){
			response.setToken(acount.getToken());
			response.setRole("manager");
			response.setQuotientScore(quotientScore);
			response.setMaxQuotientScore(1000);
		}
		acount.setQuotientScore(quotientScore);
		//redisTemplate.opsForValue().set(acount.getToken(), acount, 30, TimeUnit.DAYS);
		logger.info("帐号为："+acount.getAccount()+"的车商分为："+acount.getQuotientScore()+"(缓存),"+selectq.getQuotientScore()+"(DB)");
		BaseResult baseResult =  BaseResult.success();
		baseResult.setData(response);
		return baseResult;
	}


	/**
	 * 获取验证码
	 * @param vo
	 * @return
	 */
	public BaseResult reqValidationCode(ValidationCodeRequst vo) {
		//短信发送验证码
		boolean flag = smsService.sendSms_aliy(vo.getAccount());
		if (flag) {
			return BaseResult.success();
		} else {
			return BaseResult.fail("-1", "发送验证码异常");
		}
	}

	/**
	 * 注册
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult register(RegisterRequest vo) {
		logger.info("Register Start" + new Date());
		boolean flag = cacheService.checkCode(vo.getAccount(), vo.getCode());
		if (!flag) {
			return BaseResult.fail(UserErrorCode.CaptchaErrorRetCode, UserErrorCode.CaptchaErrorRetInfo);
		}

		Acount record = new Acount();
		record.setAccount(vo.getAccount());
		Acount acount = acountMapper.selectByModel(record);
		if (null != acount) {
			return BaseResult.fail(UserErrorCode.RegisterCode, UserErrorCode.RegisterRetInfo);
		}

		String password = vo.getAccountPassword();
		record.setAccountPassword(password);
		record.setAccountType(Integer.parseInt(vo.getAccountType()));
		long accountCode = Long.parseLong(vo.getAccount());
		record.setAccountCode(accountCode);
		record.setMerchantName(vo.getAccount()+"车行");
		record.setMobile(vo.getAccount());
		record.setCreateTime(new Date());
		//设置默认accountkey为123456
		record.setAccountKey("123456");
		//设置默认店招图片
		record.setMerchantImagePath(merchant_image_path);
		//车商分初始100分
		record.setQuotientScore((double)100);
		//注册成功自动登录 返回token
		String userToken = UUID.randomUUID().toString();
		record.setToken(userToken);
		if(vo.getInviter() != null || vo.getInviter() != ""){
			record.setInviter(vo.getInviter());
		}
		acountMapper.insertSelective(record);
		//同时注册环信
//		ClientContext clientContext = ClientContext.getInstance();
//		clientContext=clientContext.init(ClientContext.INIT_FROM_PROPERTIES);
//		EasemobRestAPIFactory factory = clientContext.getAPIFactory();
//		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//		BodyWrapper userBody = new IMUserBody(vo.getAccount(), vo.getAccountPassword(), vo.getAccount());
//		user.createNewIMUserSingle(userBody);

		RegisterHuanxin registerHuanxin = new RegisterHuanxin(vo.getAccount(),vo.getAccountPassword());
		new Thread(registerHuanxin).start();
		//registerHuanxin.run();


		//账户钱包
		Merchantwallet wallet = new Merchantwallet();
		wallet.setAccountid(record.getId());
		wallet.setAvail(new BigDecimal(0));
		wallet.setFrozen(new BigDecimal(0));
		wallet.setCreateTime(new Date());
		merchantwalletMapper.insertSelective(wallet);
		//TODO 调utonmis API生成商家静态访问文件

		//添加搜索消息通知
		Notice notice = new Notice("carsok_acount",record.getId(),0);
		noticeMapper.insert(notice);

		//登录成功信息存缓存
		try {
			redisTemplate.opsForValue().set(userToken, record, 30, TimeUnit.DAYS);
		} catch (Exception e) {
			logger.error("主账户登录token缓存异常, 用户信息为： " + JSON.toJSONString(acount), e);
			return BaseResult.fail("-1", "账户登录token缓存异常");
		}
		LoginResponse response = new LoginResponse();
		BaseResult baseResult =  BaseResult.success();
		response.setToken(userToken);
		response.setId(record.getId());
		response.setRole("manager");//主账户默认为经理角色
		response.setQuotientScore((double)100);
		response.setMaxQuotientScore(1000);
		baseResult.setData(response);
		logger.info("Register End" + new Date());
		if(StringUtil.isNotEmpty(vo.getInviter())){
			Acount records = new Acount();
			records.setAccount(vo.getInviter());
			Acount acounts = acountMapper.selectByModel(records);
			if(acounts==null)
			{
				return baseResult;
			}
			Acount acountP = new Acount();
			acountP.setId(acounts.getId());
			acountP.setAccountCode(acounts.getAccountCode());
			acountP.setAccount(acounts.getAccount());
			acountP.setAccountPassword(acounts.getAccountPassword());
			acountP.setNick(acounts.getNick()==null?null:acounts.getNick());
			acountP.setMobile(acounts.getMobile()==null?null:acounts.getMobile());
			acountP.setAccountType(acounts.getAccountType());
			acountP.setPayPassword(acounts.getPayPassword()==null?null:acounts.getPayPassword());
			acountP.setIsBk(acounts.getIsBk());
			acountP.setIsRealNameAudit(acounts.getIsRealNameAudit());
			acountP.setIsPaypwd(acounts.getIsPaypwd());
			acountP.setIsMerchantAudit(acounts.getIsMerchantAudit());
			acountP.setRealName(acounts.getRealName()==null?null:acounts.getRealName());
			acountP.setIdcard(acounts.getIdcard()==null?null:acounts.getIdcard());
			acountP.setHeadPortraitPath(acounts.getHeadPortraitPath()==null?null:acounts.getHeadPortraitPath());
			acountP.setBusinessLicencePath(acounts.getBusinessLicencePath()==null?null:acounts.getBusinessLicencePath());
			acountP.setAccountKey(acounts.getAccountKey()==null?null:acounts.getAccountKey());
			acountP.setMerchantName(acounts.getMerchantName()==null?null:acounts.getMerchantName());
			acountP.setProvince(acounts.getProvince()==null?null:acounts.getProvince());
			acountP.setCity(acounts.getCity()==null?null:acounts.getCity());
			acountP.setMerchantAddress(acounts.getMerchantAddress()==null?null:acounts.getMerchantAddress());
			acountP.setMerchantDescr(acounts.getMerchantDescr()==null?null:acounts.getMerchantDescr());
			acountP.setMerchantImagePath(acounts.getMerchantImagePath()==null?null:acounts.getMerchantImagePath());
			acountP.setCreateTime(acounts.getCreateTime());
			acountP.setUpdateTime(acounts.getUpdateTime());
			acountP.setQuotientScore(acounts.getQuotientScore()+(double)5);
			acountP.setToken(acounts.getToken()==null?null:acounts.getToken());
			acountP.setInviter(acounts.getInviter()==null?null:acounts.getInviter());
			acountP.setOpenId(acounts.getOpenId());
			acountP.setRefreshToken(acounts.getRefreshToken()==null?null:acounts.getRefreshToken());
			acountP.setWxNickName(acounts.getWxNickName()==null?null:acounts.getWxNickName());
			acountP.setWxHeadUrlString(acounts.getWxHeadUrlString()==null?null:acounts.getWxHeadUrlString());
			//redisTemplate.opsForValue().set(acounts.getToken(), acountP, 30, TimeUnit.DAYS);
			acountMapper.updateQuotientScore(acountP);
			MessageCenter mc = new MessageCenter();
			mc.setTitle("推广通知");
			mc.setContent("用户"+vo.getAccount()+"通过您的推广注册成功");
			mc.setCreateTime(new Date());
			mc.setEnable(1);
			mc.setPushTo(acounts.getAccount());
			mc.setPushFrom("systems");
			mc.setContentType("extension");
			mc.setPushStatus(1);
			int sf = messageCenterMapper.messageCenterAdd(mc);
			//给邀请人发送推广
			String messages = "您已成功邀请一位用户" + vo.getAccount() + "，4S免费维保记录将在1-2个工作日发放到您账户，注意查收。";
			boolean df = pushService.SendCustomizedCast(vo.getInviter(), messages,"Other");
			logger.info("----------推广通知:接收人: "+acounts.getAccount()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
		}
		return baseResult;
	}

	/**
	 * 找回密码
	 * @param vo
	 * @return
	 */
	public BaseResult forget(ForgetRequest vo) {
		boolean flag = cacheService.checkCode(vo.getAccount(), vo.getCode());
		if (!flag) {
			return BaseResult.fail(UserErrorCode.CaptchaErrorRetCode, UserErrorCode.CaptchaErrorRetInfo);
		}
		Acount record = new Acount();
		record.setAccount(vo.getAccount());
		Acount acount = acountMapper.selectByModel(record);
		if (null == acount) {
			return BaseResult.fail(UserErrorCode.UserNotExistErrorRetCode, UserErrorCode.UserNotExistErrorRetInfo);
		}
		acount.setAccountPassword(vo.getPassWord());

		acountMapper.updateBySelective(acount);
		UpdateHuanxin updateHuanxin = new UpdateHuanxin(acount.getAccount(),vo.getPassWord());
		new Thread(updateHuanxin).start();
		//updateHuanxin.run();
		return BaseResult.success();

	}

//	public synchronized void updateHuanXinPassword(String pass,String account){
//		huanxinPassword = pass;
//		huanxinAcount = account;
//		new Thread(){
//			public void run() {
//				try{
//					//同时修改环信密码
//					EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
//					IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//					BodyWrapper b = new ResetPasswordBody(huanxinPassword);
//					user.modifyIMUserPasswordWithAdminToken(huanxinAcount,b);
//				}catch (Exception e){
//					logger.error("修改环信密码异常，异常帐号："+huanxinAcount+",密码："+huanxinPassword);
//					logger.error(e.getMessage());
//					//do nothing
//				}
//			}
//		}.start();
//	}

	private class RegisterHuanxin implements Runnable{

		private String huanxinAccount;
		private String huanxinPassWord;

		public RegisterHuanxin(String account,String passWord){
			this.huanxinAccount=account;
			this.huanxinPassWord=passWord;
		}

		@Override
		public void run() {
			try{
				ClientContext clientContext = ClientContext.getInstance();
				clientContext=clientContext.init(ClientContext.INIT_FROM_PROPERTIES);
				EasemobRestAPIFactory factory = clientContext.getAPIFactory();
				IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
				BodyWrapper userBody = new IMUserBody(huanxinAccount, huanxinPassWord, huanxinAccount);
				user.createNewIMUserSingle(userBody);
				logger.info("注册环信帐号："+huanxinAccount+",密码："+huanxinPassWord);
			}catch (Exception e){
				logger.error("注册环信异常，异常帐号："+huanxinAccount+",密码："+huanxinPassWord);
				logger.error(e.getMessage());
			}
		}
	}

	public class UpdateHuanxin implements Runnable{
		private String huanxinAccount;
		private String huanxinPassWord;

		public UpdateHuanxin(String account,String passWord){
			this.huanxinAccount=account;
			this.huanxinPassWord=passWord;
		}

		@Override
		public void run() {
			try {
				EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
				IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
				BodyWrapper b = new ResetPasswordBody(huanxinPassWord);
				user.modifyIMUserPasswordWithAdminToken(huanxinAccount,b);
				logger.info("修改环信密码："+huanxinAccount+",密码："+huanxinPassWord);
			}
			catch(Exception e){
				logger.error("修改环信密码异常，异常帐号："+huanxinAccount+",密码："+huanxinPassWord);
				logger.error(e.getMessage());
				//logger.error("推送消息错误:"+e.getMessage());
			}
		}
	}


	/**
	 * 登出
	 * @param req
	 * @return
	 */
	public BaseResult logout(HttpServletRequest req) {
		String token = req.getHeader("token");
		if(req.getHeader("token") == null){
			token = req.getHeader("subToken");
		}
		if(token != null){
			redisTemplate.delete(token);
			iCarsokLoginService.accountLoginOut(token,new Date());
		}
		return BaseResult.success();
	}

	/**
	 * 验证用户是否存在
	 * @param vo
	 * @return
	 */
	public BaseResult isAccountExist(IsAccountExistRequest vo) {
		Acount record = new Acount();
		record.setAccount(vo.getAccount());

		List<CheckMobileExist> checkMobileExists = acountMapper.querySubUserOnlyChk(vo.getAccount());
		for(int i = 0;i<checkMobileExists.size();i++) {
			if(!checkMobileExists.get(i).getCount().equals("0")){
				if(i==Integer.valueOf(1)){
					return BaseResult.fail(ErrorCode.UserMobileChkParentFail, ErrorCode.UserMobileChkParentFailInfo);
				}else{
					return BaseResult.fail(ErrorCode.UserMoblieChkFail, ErrorCode.UserMoblieChkFailInfo);
				}
			}
		}

		/*Acount acount = acountMapper.selectByModel(record);
		if (null != acount) {

			return BaseResult.fail(UserErrorCode.RegisterCode, UserErrorCode.RegisterRetInfo);
		}*/
		return BaseResult.success();
	}

	/**
	 * 子账号登录
	 * @param request
	 * @return
	 */
	public BaseResult subLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 校验验证码
	 * @param vo
	 * @return
	 */
	public BaseResult checkCode(CheckCodeRequest vo) {
		boolean flag =cacheService.checkCode(vo.getAccount(), vo.getCode());
		if (flag) {
			return BaseResult.success();
		} else {
			return BaseResult.fail(UserErrorCode.CaptchaErrorRetCode, UserErrorCode.CaptchaErrorRetInfo);
		}
	}


	/**
	 * 获取车商昵称和头像
	 */
	public BaseResult huanxinMsg(Acount vo){
//		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
//		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//		ResponseWrapper s = (ResponseWrapper)user.getIMUserByUserName(vo.getUserName());
//		JSONObject body = JSONObject.parseObject(s.getResponseBody().toString());
//		HuanXinResponse hxr = new HuanXinResponse();
//		BaseResult baseResult =  BaseResult.success();
//		baseResult.setData(body);
//		return baseResult;
		Acount record = new Acount();
		record.setAccount(vo.getAccount());
		Acount acount = acountMapper.selectByModel(record);
		if(acount==null){
			List<ChildAccount> list = childAccountMapper.selectBymobile(vo.getAccount());
			HuanXinResponse hxr = new HuanXinResponse();
			if(list != null && list.size()>0){
				String nick = list.get(0).getChildAccountName();
				hxr.setNick(nick);
			}else {
				hxr.setNick("");
			}
			String headPath = "";
			hxr.setHeadPath(headPath);
			BaseResult baseResult =  BaseResult.success();
			baseResult.setData(hxr);
			return baseResult;
		}else{
			HuanXinResponse hxr = new HuanXinResponse();
			String nick = acount.getNick();
			if(nick==""||nick==null){
				nick="";
			}
			hxr.setNick(nick);
			String headPath = acount.getHeadPortraitPath();
			if(headPath==""||headPath==null){
				headPath="";
			}
			hxr.setHeadPath(headPath);
			BaseResult baseResult =  BaseResult.success();
			baseResult.setData(hxr);
			return baseResult;
		}

}
	public BaseResult deleteByUserName(String userName){
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		user.deleteIMUserByUserName(userName);
		return BaseResult.success();
	}


	/**
	 * 找回密码
	 * @param vo
	 * @return
	 */
	public BaseResult modifyPassword(ModifyPwdRequest vo,HttpServletRequest request) {
		if(com.uton.carsokApi.util.StringUtil.isEmpty(vo.getAccount()))
		{
			Acount account = cacheService.getAcountInfoFromCache(request);
			vo.setAccount(account.getAccount());
		}

		if (vo.getNewPassword().equals(vo.getPassword())) {
			return BaseResult.fail(UserErrorCode.OldPwdNewPwdErrorCode, UserErrorCode.OldPwdNewPwdErrorfo);
		}
		Acount record = new Acount();
		record.setAccount(vo.getAccount());
		record.setAccountPassword(vo.getPassword());
		Acount acount = acountMapper.selectByModel(record);
		if (null == acount) {
			return BaseResult.fail(UserErrorCode.OldPwdErrorRetCode, UserErrorCode.OldPwdErrorRetInfo);
		}

		acount.setAccountPassword(vo.getNewPassword());
		acountMapper.updateBySelective(acount);

		//同时修改环信密码
//		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
//		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//		BodyWrapper b = new ResetPasswordBody(vo.getNewPassword());
//		user.modifyIMUserPasswordWithAdminToken(vo.getAccount(),b);
		UpdateHuanxin updateHuanxin = new UpdateHuanxin(acount.getAccount(),vo.getNewPassword());
		new Thread(updateHuanxin).start();
		//updateHuanxin.run();
		return BaseResult.success();
	}

    public BaseResult updatePasswordByPmsAccountId(int id){
        Acount acount = acountMapper.selectByPrimaryKey(id);
        String pass = acount.getAccount()+"123";
        String newPass = MD5Util.getMD5String(pass).toUpperCase();
        UpdateHuanxin updateHuanxin = new UpdateHuanxin(acount.getAccount(),newPass);
        new Thread(updateHuanxin).start();
        return BaseResult.success();
    }
}
