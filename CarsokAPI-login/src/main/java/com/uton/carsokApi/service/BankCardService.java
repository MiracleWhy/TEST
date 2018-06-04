package com.uton.carsokApi.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.BindBankCardRequest;
import com.uton.carsokApi.controller.request.GetBankCardInfoResponse;
import com.uton.carsokApi.controller.response.IsBindCardResponse;
import com.uton.carsokApi.dao.AcountBankMapper;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.AcountBank;

/**
 * 银行卡相关service
 * @author bing.cheng
 *
 */
@Service
public class BankCardService {
	
	private final static Logger logger = Logger.getLogger(BankCardService.class);

	@Resource
	CacheService cacheService;
	
	@Autowired 
	AcountMapper acountMapper;
	
	@Autowired 
	JisuAuthService jisuAuthService;
	
	@Autowired 
	AcountBankMapper acountBankMapper;
	
	/**
	 * 是否绑定银行卡
	 * @param request
	 * @return
	 */
	public BaseResult isBindCard(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		
		IsBindCardResponse res = new IsBindCardResponse();
		res.setIsBind(acountRes.getIsBk());
		return BaseResult.success(res);
	}
	
	/**
	 * 绑定银行卡
	 * @param request
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult bindBankCard(HttpServletRequest request, BindBankCardRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		
		logger.info("绑定银行卡，入参： idcard = " 
				+ acountRes.getIdcard() + "  realName=" + acountRes.getRealName() + "  bankcard = " + vo.getBankcard());
		boolean flag = jisuAuthService.bankcardverify(acountRes.getIdcard(), acountRes.getRealName(), vo.getBankcard());
		if (!flag) {
			return BaseResult.fail(ErrorCode.BankCardBindFail, ErrorCode.BankCardBindInfo);
		}
		
		Date now = new Date();
		
		//设置账户绑定银行卡
		Acount acountUp = new Acount();
		acountUp.setId(acountRes.getId());
		acountUp.setIsBk((short) 2);
		acountUp.setUpdateTime(now);
		acountMapper.updateBySelective(acountUp);
		
		//存储银行卡
		AcountBank acountBank = new AcountBank();
		acountBank.setAccountId(acountRes.getId());
		acountBank.setBankNum(vo.getBankcard());
		acountBank.setOpenedBank(vo.getOpenedBank());
		acountBank.setCreateTime(now);
		acountBank.setUpdateTime(now);
		acountBankMapper.insertSelective(acountBank);
		
		return BaseResult.success();
	}

	/**
	 * 删除银行卡
	 * @param request
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult delBankCard(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		//删除银行卡信息
		AcountBank delRecord = new AcountBank();
		delRecord.setAccountId(acount.getId());
		acountBankMapper.deleteByByModel(delRecord);
		//更改绑定银行卡状态
		Acount upRecord = new Acount();
		upRecord.setId(acount.getId());
		upRecord.setIsBk((short) 1);
		acountMapper.updateBySelective(upRecord);
		
		return BaseResult.success();
	}

	/**
	 * 获取银行卡信息
	 * @param request
	 * @return
	 */
	public BaseResult getBankCardInfo(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		
		AcountBank record = new AcountBank();
		record.setAccountId(acountRes.getId());
		AcountBank acountBank = acountBankMapper.selectByModel(record);
		if (null == acountBank) {
			return BaseResult.success();
		}
		
		GetBankCardInfoResponse res = new GetBankCardInfoResponse();
		res.setBankNum(acountBank.getBankNum());
		res.setOpenedBank(acountBank.getOpenedBank());
		res.setRealName(acountRes.getRealName());
		
		return BaseResult.success(res);
	}


}
