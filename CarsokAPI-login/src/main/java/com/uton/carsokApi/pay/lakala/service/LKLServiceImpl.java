package com.uton.carsokApi.pay.lakala.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.constants.enums.PayWayEnum;
import com.uton.carsokApi.controller.request.LKLApplyRequest;
import com.uton.carsokApi.dao.AccountLklMapper;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.AccountLkl;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.lakala.model.LaKaLaApplyStatusEnum;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.pay.lakala.util.LaKaLaConstants;
import com.uton.carsokApi.pay.lakala.util.SignUtils;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.OrderBaseService;
import com.uton.carsokApi.service.OrderService;
import com.uton.carsokApi.service.PayBillService;
import com.uton.carsokApi.service.UploadService;
import com.uton.carsokApi.service.impl.PayHelper;

/**
 * 
 * @author jml
 *
 *         2017年3月21日
 */
@Service
public class LKLServiceImpl implements LKLService {

	public static Logger logger = LoggerFactory.getLogger(LKLServiceImpl.class);
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PayBillService payBillService;
	@Autowired
	private AcountMapper acountMapper;
	@Autowired
	private AccountLklMapper accountLklMapper;
	@Autowired
	private EventBus eventBus;
	@Resource(name = "CAR")
	private OrderBaseService orderBaseService;
	@Autowired
	private PayHelper payHelper;
	@Autowired
	private UploadService uploadService;

	@Override
	public OperateResult openPos(HttpServletRequest request) {
		OperateResult result = checkAndApplyLkl(request);
		if (result.isSuccess()) {
			result.setSuccess(false);
			result.setMessage("您已成功开通,请重新进入页面。");
			return result;
		}
		// 获取开户人的加密信息
		Acount account = cacheService.getAcountInfoFromCache(request);
		account = acountMapper.selectByModel(account);
		if (account == null) {
			return new OperateResult(false, "请登录");
		}
		Map<String, Object> paramsMap = getInitMap();
		paramsMap.put("userId", account.getAccount());
		paramsMap.put("phoneNumber", account.getAccount());
		paramsMap.put("remark", "商户开通");
		paramsMap.put("productName", "拉卡拉测试");
		paramsMap.put("productDesc", "测试订单描述");
		paramsMap.put("randnum", SignUtils.getRandomNum(9));
		paramsMap.put("timestamp", SignUtils.getTimestamp());
		paramsMap.put("expriredtime", SignUtils.getExpireTimestamp());
		paramsMap.put("optCode", "B00002");
		paramsMap.put("businessName", "商户开通");
		paramsMap.put("realName", account.getRealName());
		paramsMap.put("idCardId", account.getIdcard());
		paramsMap.put("legalPersonName", account.getRealName());
		paramsMap.put("accountName", account.getRealName());
		paramsMap.put("amount", "0.01");
		result = SignUtils.sign(paramsMap);
		return result;
	}

	@Override
	public Map<String, Object> getInitMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	@Override
	public OperateResult payCheck(JSONObject jsonObject) throws UnsupportedEncodingException {
		// 这里做paycheck认证
		OperateResult result = checkBill(jsonObject);
		String canPay = "n";
		Map<String, Object> paramsMap = getInitMap();
		paramsMap.put("partnerBillNo", jsonObject.getString("orderId"));
		if (result.isSuccess()) {
			canPay = "y";
			CarsokPayBill payBill = (CarsokPayBill) result.getData();
			paramsMap.put("partnerBillNo", payBill.getBillNo());
			paramsMap.put("parterExtendinfo", SignUtils.signRealName(payBill.getAccountRealName()));// 附加信息
		}

		// 必填
		paramsMap.put("reqId", jsonObject.getString("reqId"));
		paramsMap.put("canPay", canPay);
		paramsMap.put("partnerQueryTime", SignUtils.getTimestamp());
		paramsMap.put(LaKaLaConstants.NO_BASE64, LaKaLaConstants.NO_BASE64);
		// 签名
		result = SignUtils.sign(paramsMap);
		return result;
	}

	@Override
	public OperateResult applyNotify(JSONObject obj) {
		BaseEvent event = new BaseEvent();
		event.setData(obj.toJSONString());
		event.setEventName(EventConstants.LAKALA_ACOUNT_APPLY_NOTIFY_EVENT);
		event.setWeight("1000");
		eventBus.publish(event);
		String partnerTime = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
		String isSucccess = LaKaLaConstants.SUCCESS;
		Map map = getInitMap();
		map.put("partnerTime", partnerTime);
		map.put("isSucccess", isSucccess);
		map.put("repId", obj.getString("reqId"));
		map.put(LaKaLaConstants.NO_BASE64, LaKaLaConstants.NO_BASE64);
		OperateResult result = SignUtils.sign(map);
		return result;
	}

	@Override
	public OperateResult payNotify(JSONObject obj) {
		BaseEvent event = new BaseEvent();
		event.setData(obj.toJSONString());
		event.setEventName(EventConstants.LAKALA_PAY_NOTIFY_EVENT);
		event.setWeight("1000");
		eventBus.publish(event);
		Map<String, Object> paramsMap = getInitMap();
		paramsMap.put("repId", obj.getString("repId"));
		paramsMap.put("sid", obj.getString("sid"));
		paramsMap.put("lakalaBillNo", obj.getString("lakalaBillNo"));
		paramsMap.put("partnerBillNo", obj.getString("partnerBillNo"));
		paramsMap.put("isSuccess", "Y");
		paramsMap.put("partnerTime", DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
		paramsMap.put(LaKaLaConstants.NO_BASE64, LaKaLaConstants.NO_BASE64);
		return SignUtils.sign(paramsMap);
	}

	/**
	 * 创建支付流水
	 * 
	 * @param request
	 * @return
	 */
	@Override
	@Transactional
	public OperateResult createBill(PayBillRequest request) {
		Acount acount = request.getAcount();
		OperateResult result = orderService.createBill(request);
		if (!result.isSuccess()) {
			return result;
		}
		CarsokPayBill payBill = (CarsokPayBill) result.getData();
		Map<String, Object> map = getInitMap();
		map.put("userId", acount.getId());
		map.put("phoneNumber", acount.getAccount());
		map.put("timestamp", SignUtils.getTimestamp());
		map.put("orderId", payBill.getBillNo());
		map.put("productName", "车辆");
		map.put("productDesc", "车辆信息");
		map.put("amount", payBill.getBillMoney());
		map.put("randnum", SignUtils.getRandomNum(6));
		map.put("optCode", "P00001");
		result = SignUtils.sign(map);
		map.clear();
		map.put("sign", result.getData());
		map.put("bill", payBill);
		result.setData(map);
		return result;
	}

	private OperateResult checkBill(JSONObject jsonObject) {
		OperateResult result = new OperateResult();
		// 获取参数
		String orderId = jsonObject.getString("orderId");
		String srcOptCode = jsonObject.getString("srcOptCode");
		BigDecimal amount = new BigDecimal(jsonObject.getString("amount"));
		CarsokPayBill payBill = payBillService.queryPayBillForUpdate(orderId);
		// 验证支付流水是否存在
		if (payBill == null) {
			return result;
		}
		// 验证金额是否正确
		if (payBill.getBillMoney().compareTo(amount) != 0) {
			return result;
		}
		// 验证提交的操作码
		if (StringUtils.equals(srcOptCode, "")) {

		}
		// 验证提交的查询时间
		result.setSuccess(true);
		result.setData(payBill);
		return result;
	}

	@Override
	public OperateResult checkAndApplyLkl(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);

		OperateResult result = new OperateResult();
		if (acount == null) {
			result.setMessage("请登录");
			return result;
		}
		AccountLkl accountLkl = accountLklMapper.selectByAccountIdForUpdate(acount.getAccount());
		if (accountLkl == null) {
			result.setMessage("商户未开通");
			return result;
		}
		result.setData(accountLkl);
		result.setSuccess(true);
		return result;
	}

	@Override
	public OperateResult createTestBill(Acount acount, AccountLkl accountLkl) throws Exception {
		CarsokOrder order = orderService.queryTestOrderForUpdate();
		if (order == null) {
			order = new CarsokOrder();
			order.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
			order.setTotalMoney(new BigDecimal(100000000));
			order.setType(OrderTypeEnum.TEST);
			order = (CarsokOrder) orderService.create(order).getData();
		}
		PayBillRequest billRequest = new PayBillRequest();
		billRequest.setAcount(acount);
		billRequest.setMoney(new BigDecimal("0.01"));
		billRequest.setOrderNo(order.getOrderNo());
		billRequest.setMemo(OrderTypeEnum.TEST.name());
		billRequest.setAccountRealName(accountLkl.getRealName());
		OperateResult result = orderService.createBill(billRequest);
		if (!result.isSuccess()) {

		}
		CarsokPayBill payBill = (CarsokPayBill) result.getData();
		billRequest.setBillNo(payBill.getBillNo());
		billRequest.setPayWay(PayWayEnum.LAKALA);
		return payHelper.sign(billRequest);
	}

	@Override
	public OperateResult uploadApplyImg(HttpServletRequest request, String image, AccountLkl accountLkl)
			throws IOException {
		String url = uploadService.upload(request, image);
		accountLkl.setGmtModify(new Date());
		accountLkl.setApplyStatus(LaKaLaApplyStatusEnum.WAIT_REVIEW);
		accountLkl.setApplyImage(url);
		accountLklMapper.updateByPrimaryKeySelective(accountLkl);
		return new OperateResult(true, "");
	}

	@Override
	@Transactional
	public OperateResult lklApplyHandler(HttpServletRequest request,LKLApplyRequest lklRequest) throws Exception {
		Acount account = cacheService.getAcountInfoFromCache(request);
		if (account == null) {
			return new OperateResult(false, "请登录");
		}
		if(StringUtils.isNotEmpty(account.getSubPhone())){
			return new OperateResult(false, "请使用主账号进行开通测试");
		}
		AccountLkl accountLkl = accountLklMapper.selectByAccountIdForUpdate(account.getAccount());
		if (accountLkl == null) {
			return new OperateResult(false, "商户未开通");
		}
		OperateResult result = null;
		LaKaLaApplyStatusEnum applyStatus = accountLkl.getApplyStatus();
		if (StringUtils.equals(accountLkl.getOpenStatus(), LaKaLaConstants.SUCCESS)) {
			return new OperateResult(true, "商户已成功开通");
		}
		if (applyStatus == LaKaLaApplyStatusEnum.WAIT_TRADE_TEST) {
			result = createTestBill(account, accountLkl);
		} else if (applyStatus == LaKaLaApplyStatusEnum.WAIT_UPLOAD_TRADE_CERTIFICATE) {
			if(lklRequest==null){
				return new OperateResult(false, "图片附件为空");
			}
			String imageStr = lklRequest.getImgStr();
			if (StringUtils.isEmpty(imageStr)) {
				return new OperateResult(false, "图片附件为空");
			}
			result = uploadApplyImg(request, imageStr, accountLkl);
		}
		return result;
	}

}
