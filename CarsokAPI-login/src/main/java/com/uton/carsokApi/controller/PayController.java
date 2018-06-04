package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.controller.request.ForgetPayPwdRequest;
import com.uton.carsokApi.controller.request.PayDetailRequest;
import com.uton.carsokApi.controller.request.PosPayRequest;
import com.uton.carsokApi.controller.request.SetPayPwdRequest;
import com.uton.carsokApi.controller.request.UpPayPwdRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.PayService;
import com.uton.carsokApi.service.impl.PayHelper;

/**
 * 支付相关 controller
 * @author bing.cheng
 *
 */
@Controller
public class PayController {

	private final static Logger logger = Logger.getLogger(PayController.class);
	
	@Autowired
	PayService payService;
	@Autowired
	private PayHelper payHelper;
	@Autowired
	private CacheService cacheService;
	/**
	 * 设置支付密码
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/setPayPwd" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult setPayPwd(HttpServletRequest request, @RequestBody SetPayPwdRequest vo) {
		try {
			return payService.setPayPwd(request, vo);
		} catch (Exception e) {
			logger.error("setPayPwd error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 校验支付密码
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/checkPayPwd" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult checkPayPwd(HttpServletRequest request, @RequestBody SetPayPwdRequest vo) {
		try {
			return payService.checkPayPwd(request, vo);
		} catch (Exception e) {
			logger.error("setPayPwd error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 修改支付密码
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/upPayPwd" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult upPayPwd(HttpServletRequest request, @RequestBody UpPayPwdRequest vo) {
		try {
			if (vo.getNwPayPassword().equals(vo.getPayPassword())) {
				return BaseResult.fail(UserErrorCode.OldPwdNewPwdErrorCode, UserErrorCode.OldPwdNewPwdErrorfo);
			}
			return payService.upPayPwd(request, vo);
		} catch (Exception e) {
			logger.error("upPayPwd error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 忘记支付密码-重设支付密码
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/forgetPayPwd" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult resetPayPwd(HttpServletRequest request, @RequestBody  ForgetPayPwdRequest vo) {
		try {
			return payService.forgetPayPwd(request, vo);
		} catch (Exception e) {
			logger.error("forgetPayPwd error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	
	/**
	 * 支付明细
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/payDetail" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult payDetail(HttpServletRequest request, @RequestBody PayDetailRequest vo) {
		try {
			return payService.payDetail(request, vo);
		} catch (Exception e) {
			logger.error("payDetail error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 获取待支付列表
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/getToPayList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getToPayList(HttpServletRequest request) {
		try {
			return payService.getToPayList(request);
		} catch (Exception e) {
			logger.error("getToPayList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 获取已经支付列表
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/getPayedList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getPayedList(HttpServletRequest request) {
		try {
			return payService.getPayedList(request);
		} catch (Exception e) {
			logger.error("getPayedList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 是否设置了支付密码
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/isSetPayPwd" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult isSetPayPwd(HttpServletRequest request) {
		try {
			return payService.isSetPayPwd(request);
		} catch (Exception e) {
			logger.error("isSetPayPwd error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * pos收款
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/posPay" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult posPay(HttpServletRequest request,@RequestBody PosPayRequest vo) {
		try {
			return payService.posGoPay(request, vo);
		} catch (Exception e) {
			logger.error("posPay error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * pos收款前置条件
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/checkPosPay" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult checkPosPay(HttpServletRequest request) {
		try {
			return payService.checkPosPay(request);
		} catch (Exception e) {
			logger.error("posPay error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	@RequestMapping(value = { "/getPaySign" }, method = { RequestMethod.GET })
	public @ResponseBody OperateResult getPaySign(HttpServletRequest request,PayBillRequest payRequest) {
		try {
			Acount acount=cacheService.getAcountInfoFromCache(request);
			if(acount==null){
				return new OperateResult(false,"请登录");
			}
			payRequest.setAcount(acount);
			payRequest.setIp(request.getRemoteAddr());
			return payHelper.sign(payRequest);
		} catch (Exception e) {
			logger.error("posPay error:", e);
			return new OperateResult(false, "");
		}
	}
	@RequestMapping("/pay/callBack/{path}")
	public String payCallBackPage( @PathVariable("path") String path){
		return "/"+path;
	}
}
