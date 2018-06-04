package com.uton.carsokApi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.ApplyCashRequest;
import com.uton.carsokApi.controller.response.CashListResponse;
import com.uton.carsokApi.dao.AcountBankMapper;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.AcountWithdrawCashMapper;
import com.uton.carsokApi.dao.MerchantwalletMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.AcountBank;
import com.uton.carsokApi.model.AcountWithdrawCash;
import com.uton.carsokApi.model.Merchantwallet;
import com.uton.carsokApi.util.DateStyle;
import com.uton.carsokApi.util.DateUtil;

/**
 * 商家提现
 * @author bing.cheng
 *
 */
@Service
public class WithdrawCashService {

	@Autowired
	AcountWithdrawCashMapper acountWithdrawCashMapper;
	
	@Resource
	CacheService cacheService;
	
	@Autowired 
	AcountMapper acountMapper;
	
	@Autowired 
	AcountBankMapper acountBankMapper;
	
	@Autowired
	MerchantwalletMapper merchantwalletMapper;
	/**
	 * 申请提现
	 * @param request
	 * @param vo 
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult applyCash(HttpServletRequest request, ApplyCashRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		
		AcountBank acountBankQuery = new AcountBank();
		acountBankQuery.setAccountId(acountRes.getId());
		AcountBank acountBank = acountBankMapper.selectByModel(acountBankQuery);
		if (null == acountBank) {
			return BaseResult.fail(ErrorCode.NoBankCardBindFail, ErrorCode.NoBankCardBindInfo);
		}
		//判断账户钱包可用余额是否大于提现金额
		Merchantwallet wallet = new Merchantwallet();
		wallet.setAccountid(acountRes.getId());
		wallet = merchantwalletMapper.selectByModel(wallet);
		BigDecimal amount = new BigDecimal(vo.getAmount());
		int i = wallet.getAvail().compareTo(amount);
		if(i == -1){
			return BaseResult.fail(ErrorCode.CashFail, ErrorCode.CashFailInfo);
		}
		//提现申请入库
		AcountWithdrawCash record = new AcountWithdrawCash();
		record.setAmount(new BigDecimal(vo.getAmount()));
		record.setAccountId(acountRes.getId());
		record.setBankNum(acountBank.getBankNum());
		record.setOpenedBank(acountBank.getOpenedBank());
		record.setRealName(acountRes.getRealName());
		record.setStatus((short) 1);
		Date now = new Date();
		record.setCreateTime(now);
		record.setUpdateTime(now);
		acountWithdrawCashMapper.insertSelective(record);
		//可用金额减少 冻结金额增加
		wallet.setAvail(wallet.getAvail().subtract(amount));
		wallet.setFrozen(wallet.getFrozen().add(amount));
		merchantwalletMapper.updateByPrimaryKeySelective(wallet);
		return BaseResult.success();
	}

	/**
	 * 提现列表
	 * @param request
	 * @return
	 */
	public BaseResult cashList(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		AcountWithdrawCash query = new AcountWithdrawCash();
		query.setAccountId(acount.getId());
		List<AcountWithdrawCash> queryList = acountWithdrawCashMapper.selectListByModel(query);
		if (null == queryList || queryList.size() <= 0) {
			return BaseResult.success();
		}
		List<CashListResponse> resList = new ArrayList<>();
		for (AcountWithdrawCash cash : queryList) {
			CashListResponse model = new CashListResponse();
			model.setAmount(cash.getAmount().toString());
			model.setCreateTime(DateUtil.DateToString(cash.getCreateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			model.setStatus(cash.getStatus());
			model.setWithdrawNum(cash.getWithdrawNum());
			resList.add(model);
		}
		
		return BaseResult.success(resList);
	}

}
