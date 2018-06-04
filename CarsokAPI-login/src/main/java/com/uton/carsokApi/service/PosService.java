package com.uton.carsokApi.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.constants.enums.PosApplyStatusEnum;
import com.uton.carsokApi.constants.enums.PosOpenStatusEnum;
import com.uton.carsokApi.controller.request.OpenPosRequest;
import com.uton.carsokApi.controller.request.UpPosPwdReuqest;
import com.uton.carsokApi.controller.response.GetPosInfoResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.AcountPosMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.AcountPos;
import com.uton.carsokApi.util.PassWordUtil;

/**
 * pos支付相关
 * @author bing.cheng
 *
 */
@Service
public class PosService {

	@Resource
	CacheService cacheService;
	
	@Autowired 
	AcountPosMapper acountPosMapper;
	
	@Autowired 
	AcountMapper acountMapper;
	
	/**
	 * 获取pos信息
	 * @param request
	 * @return
	 */
	public BaseResult getPosInfo(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		AcountPos posQuery = new AcountPos();
		posQuery.setAccountId(acount.getId());
		AcountPos pos = acountPosMapper.selectByModel(posQuery);
		if (null == pos) {
			return BaseResult.success();
		}
		
		GetPosInfoResponse res = new GetPosInfoResponse();
		res.setAccountId(pos.getAccountId());
		res.setApplySatus(pos.getApplyStatus());
		
		res.setId(pos.getId());
		res.setOpenStatus(pos.getOpenStatus());
		res.setPosLoginAccount(pos.getPosLoginAccount());
		res.setPosSn(pos.getPosSn());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM"); 
		res.setCreateTime(sdf.format(pos.getCreateTime()));
		res.setUpdateTime(sdf.format(pos.getUpdateTime()));
		return BaseResult.success(res);
	}

	/**
	 * 领取pos机
	 * @param request
	 * @return
	 */
	public BaseResult receivePos(HttpServletRequest request) {
		//查询数据库有没有记录，如果没有就记录一条，返回成功（线下领取），如果有就返回已经领取
		Acount acount = cacheService.getAcountInfoFromCache(request);
		AcountPos posQuery = new AcountPos();
		posQuery.setAccountId(acount.getId());
		AcountPos pos = acountPosMapper.selectByModel(posQuery);
		if (null != pos) {
			return BaseResult.success("已经领取");
		}
		
		AcountPos inModel = new AcountPos();
		inModel.setAccountId(acount.getId());
		Date now = new Date();
		inModel.setCreateTime(now);
		inModel.setUpdateTime(now);
		inModel.setApplyStatus(PosApplyStatusEnum.APPLING.getCode().shortValue());
		inModel.setOpenStatus(PosOpenStatusEnum.OPEN_NO.getCode().shortValue());
		acountPosMapper.insertSelective(inModel);
		
		return BaseResult.success("领取审核信息提交");
	}

	/**
	 * 开通pos机
	 * @param request
	 * @param vo 
	 * @return
	 */
	public BaseResult openPos(HttpServletRequest request, OpenPosRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		AcountPos posQuery = new AcountPos();
		posQuery.setAccountId(acount.getId());
		AcountPos pos = acountPosMapper.selectByModel(posQuery);
		if (null == pos) {
			return BaseResult.fail("-1", "未找到记录");
		}
		
		AcountPos upModel = new AcountPos();
		Date now = new Date();
		upModel.setId(pos.getId());
		upModel.setUpdateTime(now);
		//pos密码不需盐值进行加密 第三方支付要求
		upModel.setPosLoginPasswd(PassWordUtil.getPosPassWord(null, vo.getPosLoginPasswd()));
		upModel.setApplyStatus(PosApplyStatusEnum.APPL_YES.getCode().shortValue());
		upModel.setOpenStatus(PosOpenStatusEnum.OPEN_YES.getCode().shortValue());
		acountPosMapper.updateByPrimaryKeySelective(upModel);
		
		return BaseResult.success();
	}

	/**
	 * 修改pos 机登录密码
	 * @param request
	 * @param vo 
	 * @return
	 */
	public BaseResult upPosPwd(HttpServletRequest request, UpPosPwdReuqest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		AcountPos posQuery = new AcountPos();
		posQuery.setAccountId(acount.getId());
		AcountPos pos = acountPosMapper.selectByModel(posQuery);
		
		String oldPas = PassWordUtil.getPosPassWord(null, vo.getPosLoginPasswd());
		if (!oldPas.equals(pos.getPosLoginPasswd())) {
			return BaseResult.fail(UserErrorCode.OldPwdErrorRetCode, UserErrorCode.OldPwdErrorRetInfo);
		}
		
		AcountPos upModel = new AcountPos();
		Date now = new Date();
		upModel.setId(pos.getId());
		upModel.setUpdateTime(now);
		
		String newPas = vo.getNewPosLoginPasswd();
		upModel.setPosLoginPasswd(PassWordUtil.getPosPassWord(null, newPas));
		acountPosMapper.updateByPrimaryKeySelective(upModel);
		
		return BaseResult.success();
	}

}
