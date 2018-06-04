package com.uton.carsokApi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.controller.request.ApplyFundsRequest;
import com.uton.carsokApi.controller.response.ApplyFundsListResponse;
import com.uton.carsokApi.controller.response.ApplyFundsVo;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ApplyFundsMapper;
import com.uton.carsokApi.dao.PictureMapper;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ApplyFunds;
import com.uton.carsokApi.model.Picture;
import com.uton.carsokApi.model.Product;
import com.uton.carsokApi.util.DateStyle;
import com.uton.carsokApi.util.DateUtil;

/**
 * 配资相关service
 * @author bing.cheng
 *
 */
@Service
public class ApplyFundsService {

	@Autowired
	ApplyFundsMapper applyFundsMapper;
	
	@Autowired 
	AcountMapper acountMapper;
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	PictureMapper pictureMapper;
	
	@Resource
	CacheService cacheService;
	
	/**
	 * 申请配资
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult applyFunds(HttpServletRequest request, ApplyFundsRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		ApplyFunds record = new ApplyFunds();
		record.setAccountId(acount.getId());
		record.setAmount(new BigDecimal(vo.getAmount()).multiply(new BigDecimal(10000)));
		record.setProductId(vo.getProductId());
		Date now = new Date();
		record.setCreateTime(now);
		record.setUpdateTime(now);
		applyFundsMapper.insertSelective(record);
		return BaseResult.success();
	}
	
	public BaseResult checkApplyfunds(HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		int realNameFlag = acountRes.getIsRealNameAudit(); //是否实名认证标识
		int isMerchantAuditFlag = acountRes.getIsMerchantAudit(); //是否商家认证标识
		if( realNameFlag == 1 ){
			return BaseResult.fail(UserErrorCode.RealNameFail,UserErrorCode.RealNameFailInfo);
		}
		
		if( isMerchantAuditFlag == 1 ){
			return BaseResult.fail(UserErrorCode.MerchantAuditFail,UserErrorCode.MerchantAuditFailInfo);
		}
		return BaseResult.success();
	}

	/**
	 * 获取配资列表
	 * @param request
	 * @return
	 */
	public BaseResult getApplyFundsList(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		int realNameFlag = acountRes.getIsRealNameAudit(); //是否实名认证标识
		int isMerchantAuditFlag = acountRes.getIsMerchantAudit(); //是否商家认证标识
		ApplyFundsListResponse resp = new ApplyFundsListResponse();
		resp.setIsrealName(realNameFlag);
		resp.setIsmerchantAudit(isMerchantAuditFlag);
		ApplyFunds record = new ApplyFunds();
		record.setAccountId(acount.getId());
		List<ApplyFunds> list = applyFundsMapper.selectListByModel(record);
		if (null == list || list.size() <= 0) {
			return BaseResult.success(resp);
		}
		List<ApplyFundsVo> applylist = new ArrayList<ApplyFundsVo>();
		for (ApplyFunds applyFunds : list) {
			ApplyFundsVo vo = new ApplyFundsVo();
			vo.setAmount(applyFunds.getAmount().divide(new BigDecimal(10000)).toString());
			vo.setStatus(applyFunds.getStatus());
			Product productQuery = new Product();
			productQuery.setId(applyFunds.getProductId());
			Product product = productMapper.selectByModel(productQuery);
			Picture pictureQuery = new Picture();
			pictureQuery.setProductId(product.getId());
			short pictype = 1;
			pictureQuery.setType(pictype); //只查询主图
			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
			vo.setProductImgUrl(pictureList.get(0).getPicPath());
			vo.setProductName(product.getProductName());
			vo.setApplyTime(applyFunds.getCreateTime());
			applylist.add(vo);
		}
		resp.setApplylist(applylist);
		return BaseResult.success(resp);
	}

	public BaseResult ApplyFundsIf(HttpServletRequest request,ApplyFundsRequest vo){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		ApplyFunds record = new ApplyFunds();
		record.setProductId(vo.getProductId());
		ApplyFunds recordStatus = applyFundsMapper.selectStatusByModel(record);
		BaseResult baseResult =  BaseResult.success();
		if(recordStatus==null){
		   baseResult.setData("2");
		}else if(recordStatus.getStatus()==1){
		   baseResult.setData("1");
		}else if(recordStatus.getStatus()==2){
		   baseResult.setData("0");
		}else{
		   baseResult.setData("2");
		}
		return baseResult;
	}
	

}
