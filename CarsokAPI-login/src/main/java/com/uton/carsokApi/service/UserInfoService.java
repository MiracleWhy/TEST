package com.uton.carsokApi.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.controller.request.AccountAuthRequest;
import com.uton.carsokApi.controller.request.BusinessLicenseRequest;
import com.uton.carsokApi.controller.response.UserInfoResponse;
import com.uton.carsokApi.dao.AcountBankMapper;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.ChildRoleMapper;
import com.uton.carsokApi.dao.MerchantwalletMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.AcountBank;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.ChildRole;
import com.uton.carsokApi.model.Merchantwallet;
import com.uton.carsokApi.util.StringUtil;

/**
 * 用户信息相关
 * @author bing.cheng
 *
 */
@Service
public class UserInfoService {

	@Resource
	RedisTemplate redisTemplate;

	@Resource
	CacheService cacheService;
	
	@Autowired
	UploadService uploadService;
	
	@Autowired 
	AcountMapper acountMapper;
	
	@Autowired
	MerchantwalletMapper merchantwalletMapper;
	
	@Autowired 
	AcountBankMapper acountBankMapper;
	
	@Autowired
	ChildAccountMapper childAccountMapper;
	
	@Autowired
	ChildRoleMapper roleMapper;
	
	@Value("${share.url}")
	private String shareUrl;
	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public BaseResult getUserInfo(HttpServletRequest request) throws UnsupportedEncodingException {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		if(acountRes == null){
			return BaseResult.fail("-8","还未登录");
		}
		Merchantwallet walletQuery = new Merchantwallet();
		walletQuery.setAccountid(acount.getId());
		Merchantwallet wallet = merchantwalletMapper.selectByModel(walletQuery);
		
		UserInfoResponse res = new UserInfoResponse();
		res.setId(acountRes.getId());
		res.setAccount(acountRes.getAccount());
		res.setAccountCode(acountRes.getAccountCode());
		res.setAvail(wallet.getAvail());
		res.setFrozen(wallet.getFrozen());
		res.setHeadPortraitPath(acountRes.getHeadPortraitPath());
		res.setIsBk(acountRes.getIsBk());
		res.setIsMerchantAudit(acountRes.getIsMerchantAudit());
		res.setIsPaypwd(acountRes.getIsPaypwd());
		res.setIsRealNameAudit(acountRes.getIsRealNameAudit());
		res.setMerchantAddress(acountRes.getMerchantAddress());
		res.setMerchantDescr(acountRes.getMerchantDescr());
		res.setMerchantImagePath(acountRes.getMerchantImagePath());
		res.setMerchantName(acountRes.getMerchantName());
		res.setMobile(acountRes.getMobile());
		res.setNick(acountRes.getNick());
		res.setRealName(acountRes.getRealName());
		res.setIdcard(acountRes.getIdcard());
		res.setProvince(acountRes.getProvince());
		res.setCity(acountRes.getCity());
		res.setBusinessLicencePath(acountRes.getBusinessLicencePath());
		res.setAccount_key(acountRes.getAccountKey());
		res.setShare_url(shareUrl);
		res.setRefreshToken(acountRes.getRefreshToken());
		res.setWxHeadUrlString(acountRes.getWxHeadUrlString());
		if(acountRes.getWxNickName()!=null){
			res.setWxNickName(new String(acountRes.getWxNickName(),"UTF-8"));
		}
		res.setOpenId(acountRes.getOpenId());
		res.setAccountApproveStatus(acountRes.getAccountApproveStatus());
		return BaseResult.success(res);
	}

	/**
	 * 申请商家三无认证
	 * @param request
	 * @param vo
	 * @return
	 * @throws IOException 
	 */
	public BaseResult applyBusinessLicense(HttpServletRequest request, BusinessLicenseRequest vo) throws IOException {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount upQuery = new Acount();
		upQuery.setId(acount.getId());
		String url="";
		if(vo.getLicenceURLPath()!=null&&!"".equals(vo.getLicenceURLPath())){
			url=vo.getLicenceURLPath();
		}
		if(vo.getLicencePath()!=null&&!"".equals(vo.getLicencePath())){
			url=uploadService.upload(request, vo.getLicencePath());
		}
		upQuery.setBusinessLicencePath(url);
		upQuery.setBusinessLicencePathRefuse(url);
		upQuery.setAccountApproveStatus(Integer.valueOf(1));
		acountMapper.updateBySelective(upQuery);

		return BaseResult.success();
	}

	/**
	 * 商家认证状态查询
	 */
//	public BaseResult applyBusinessLicenseIf(HttpServletRequest request){
//		Acount acount = cacheService.getAcountInfoFromCache(request);
//		Acount acountQuery = new Acount();
//		acountQuery.setAccount(acount.getAccount());
//		Acount businessLicenseStatus = acountMapper.selectByModel(acountQuery);
//		BaseResult baseResult = BaseResult.success();
//		if(businessLicenseStatus.getIsMerchantAudit()==1){
//			baseResult.setData("1");
//		}else{
//			baseResult.setData("2");
//		}
//		return baseResult;
//	}

	/**
	 * 账户绑卡、提现前置判断
	 * 1：绑卡 2：
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult checkAccountAuth(HttpServletRequest request, AccountAuthRequest vo){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		Integer type = vo.getType();
		switch ( type ) {
		case 1:
			return bandBankCard(acountRes);
		case 2:
			return withDraw(acountRes);
		}
		return null;
	}
	
	/**
	 * 绑卡前置判断
	 * 实名认证>设置支付密码
	 * @param account
	 * @return
	 */
	public BaseResult bandBankCard(Acount account){
		short isRealNameAudit = account.getIsRealNameAudit();
		short isSetPayPwd = account.getIsPaypwd();
		if( isRealNameAudit == 1 ){
			return BaseResult.fail(UserErrorCode.RealNameFail, UserErrorCode.RealNameFailInfo);
		}else{
			if( isSetPayPwd == 1 ){
				return BaseResult.fail(UserErrorCode.IsSetPayPwdFail, UserErrorCode.IsSetPayPwdFailInfo);
			}
		}
		Map<String,String> respMap = new HashMap<String, String>();
		respMap.put("realname", account.getRealName());
		respMap.put("idcard", account.getIdcard());
		return BaseResult.success(respMap);
	}
	
	/**
	 * 提现前置判断
	 * 实名认证>设置支付密码>绑定银行卡
	 * @param account
	 * @return
	 */
	public BaseResult withDraw(Acount account){
		short isRealNameAudit = account.getIsRealNameAudit();
		short isSetPayPwd = account.getIsPaypwd();
		short isBandBankCard = account.getIsBk();
		if( isRealNameAudit == 1 ){
			return BaseResult.fail(UserErrorCode.RealNameFail, UserErrorCode.RealNameFailInfo);
		}else{
			if( isSetPayPwd == 1 ){
				return BaseResult.fail(UserErrorCode.IsSetPayPwdFail, UserErrorCode.IsSetPayPwdFailInfo);
			}else{
				if( isBandBankCard == 1){
					return BaseResult.fail(UserErrorCode.BandBankCardFail, UserErrorCode.BandBankCardFailInfo);
				}
			}
		}
		AcountBank record = new AcountBank();
		record.setAccountId(account.getId());
		AcountBank acountBank = acountBankMapper.selectByModel(record);
		Map<String,String> respMap = new HashMap<String, String>();
		respMap.put("banknum", acountBank.getBankNum());
		respMap.put("openbank", acountBank.getOpenedBank());
		return BaseResult.success(respMap);
	}
	
	public BaseResult isAuth(String mobile){
		String mMobile = "";
		Acount account = acountMapper.selectByMobile(mobile);
		//主账号
		if(account!=null){
			mMobile=mobile;
		}else{
			//子账号
			List<ChildAccount> list = childAccountMapper.selectBymobile(mobile);
			if(list!=null && list.size()>0){
				ChildAccount childAccount = list.get(0);
				List<ChildRole> rolelist = roleMapper.selectBychildId(childAccount.getId());
				mMobile=childAccount.getAccountPhone();
			}
		}

		//区分是否认证
		Acount mAccount = new Acount();
		mAccount.setAccount(mMobile);
		mAccount = acountMapper.selectByModel(mAccount);
		if(mAccount!=null){
//			if(account.getIsRealNameAudit() == 1){
//				return BaseResult.fail(UserErrorCode.RealNameFail, UserErrorCode.RealNameFailInfo);
//			}
			if(mAccount.getIsMerchantAudit() == 1){
				return BaseResult.fail(UserErrorCode.MerchantAuditFail, UserErrorCode.MerchantAuditFailInfo);
			}
		}
		return BaseResult.success();
	}
	
	/**
	 * 
	 * @param mobile
	 * @return
	 * isMastaccount 是否为主账户
	 * isAuth 为子账户时，是否是授权为评估师 pgs
	 * mastaccount 为子账户时，隶属哪个主账户
	 */
	public BaseResult checkAccount(String mobile){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Acount account = acountMapper.selectByMobile(mobile);
		if(account!=null){
			resultMap.put("isMastaccount", true);  
			resultMap.put("isAuth", null);
			resultMap.put("mastaccount", null);
		}else{
			List<ChildAccount> list = childAccountMapper.selectBymobile(mobile);
			if(list!=null && list.size()>0){
				ChildAccount childAccount = list.get(0);
				List<ChildRole> rolelist = roleMapper.selectBychildId(childAccount.getId());
				boolean flag = false;
				for(ChildRole role:rolelist){
					if(role.getRoleName().equals("pgs")){
						flag = true;
					}
				}
				if(flag){
					resultMap.put("isMastaccount", false);
					resultMap.put("isAuth", true);
					resultMap.put("mastaccount", childAccount.getAccountPhone());
				}else{
					resultMap.put("isMastaccount", false);
					resultMap.put("isAuth", false);
					resultMap.put("mastaccount", childAccount.getAccountPhone());
				}
			}
		}
		return BaseResult.success(resultMap);
	}

	public BaseResult businessRefuse(HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acount1 = new Acount();
		acount1.setAccount(acount.getAccount());
		Acount acount2 = acountMapper.selectByModel(acount1);
		BaseResult baseResult = BaseResult.success();
		baseResult.setData(acount2);
		return baseResult;
	}

	public BaseResult getUserInfoAll(HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		BaseResult baseResult = BaseResult.success();
		baseResult.setData(acount);
		return baseResult;
	}
}
