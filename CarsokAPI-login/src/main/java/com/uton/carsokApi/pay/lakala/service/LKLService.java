package com.uton.carsokApi.pay.lakala.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.controller.request.LKLApplyRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.AccountLkl;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;

/**
 * 拉卡拉服务
 * 
 * @author jml
 *
 *         2017年3月21日
 */
public interface LKLService {




	/**
	 * 跳转到商户开户页面
	 */
	public OperateResult openPos(HttpServletRequest request);

	/**
	 * 第三方支付确认
	 * @throws UnsupportedEncodingException 
	 */
	public OperateResult payCheck(JSONObject jsonObject) throws UnsupportedEncodingException;

	/**
	 * 申请通知
	 * 
	 * @param obj
	 * @return
	 */
	public OperateResult applyNotify(JSONObject obj);

	/**
	 * 支付通知
	 * 
	 * @param obj
	 * @return
	 */
	public OperateResult payNotify(JSONObject obj);

	/**
	 * 创建流水
	 * 
	 * @param request
	 * @return
	 */
	public OperateResult createBill(PayBillRequest request);

	public Map<String, Object> getInitMap();

	public OperateResult checkAndApplyLkl(HttpServletRequest request);

	public OperateResult createTestBill(Acount acount,AccountLkl accountLkl) throws Exception;
	
	public OperateResult uploadApplyImg(HttpServletRequest request,String image,AccountLkl accountLkl) throws IOException;
	
	public OperateResult lklApplyHandler(HttpServletRequest request,LKLApplyRequest lklRequest) throws Exception;
}
