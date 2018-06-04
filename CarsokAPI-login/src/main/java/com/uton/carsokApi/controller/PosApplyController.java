package com.uton.carsokApi.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.constants.enums.PosApplyStatusEnum;
import com.uton.carsokApi.controller.request.PosApplyRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.PosApply;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.PosApplyService;
import com.uton.carsokApi.service.impl.OrderHelper;
@Controller
@RequestMapping("pos")
public class PosApplyController {
	@Autowired
	private PosApplyService posApplyService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private OrderHelper orderHelper;
	/**
	 * 查看apply状态
	 * @return
	 */
	@RequestMapping(value="apply",method=RequestMethod.GET)
	@ResponseBody
	public OperateResult applyInfo(PosApplyRequest applyRequest,HttpServletRequest request){
		Acount acount=cacheService.getAcountInfoFromCache(request);
		if(acount==null){
			return new OperateResult(false, "请登录");
		}
		//获取登录人信息
		//返回状态
		PosApply posApply=posApplyService.queryApplyInfo(acount.getMobile());
		return new OperateResult(true,"",posApply);
	}
	/**
	 * 申请并去支付
	 */
	@RequestMapping(value="apply",method=RequestMethod.POST)
	@ResponseBody
	public OperateResult apply(@RequestBody PosApplyRequest applyRequest,HttpServletRequest request){
		//提交申请
		Acount acount=cacheService.getAcountInfoFromCache(request);
		if(acount==null){
			return new OperateResult(false, "请登录");
		}
		applyRequest.setAcount(acount);
		applyRequest.setAccountId(acount.getMobile());
		applyRequest.setType(OrderTypeEnum.POS_APPLY);
		OperateResult result=orderHelper.createOrder(applyRequest);
		return result;
	}
	@RequestMapping(value="receive",method=RequestMethod.POST)
	@ResponseBody
	public OperateResult receive(@RequestBody PosApplyRequest applyRequest,HttpServletRequest request){
		//提交申请
		Acount acount=cacheService.getAcountInfoFromCache(request);
		if(acount==null){
			return new OperateResult(false, "请登录");
		}
		if(StringUtils.isEmpty(applyRequest.getId())){
			return new OperateResult(false, "参数不能为空");
		}
		PosApply posApply=new PosApply();
		posApply.setId(Integer.valueOf(applyRequest.getId()));
		posApply.setApplyStatus(PosApplyStatusEnum.APPLY_SUCCESS);
		posApply.setGmtEnd(new Date());
		posApply.setMemo(StringUtils.isEmpty(acount.getSubPhone())?acount.getMobile():acount.getSubPhone());
		return posApplyService.modify(posApply);
	}

}
