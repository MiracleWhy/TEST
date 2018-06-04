package com.uton.carsokApi.controller;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.controller.response.OrderStatusInfo;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.OrderService;
import com.uton.carsokApi.service.PayBillService;
import com.uton.carsokApi.service.impl.OrderHelper;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CacheService cacheService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PayBillService payBillService;
	@Autowired
	private OrderHelper orderHelper;
	@Resource
    private RedisTemplate redisTemplate;
	public static Logger logger = LoggerFactory.getLogger(OrderController.class);

	/**
	 * 创建支付流水
	 * 
	 * @param request
	 * @param orderNo
	 * @param money
	 * @return
	 */
	@RequestMapping("/createBill")
	@ResponseBody
	public OperateResult createBill(HttpServletRequest request, String orderNo, BigDecimal money) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		if (acount == null) {
			// TODO 没哟权限
			return new OperateResult(false, "请重新登陆");
		}
		PayBillRequest billRequest = new PayBillRequest();
		billRequest.setMoney(money);
		billRequest.setOrderNo(orderNo);
		billRequest.setAcount(acount);
		OperateResult result = new OperateResult();
		try {
			result = orderService.createBill(billRequest);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(ErrorCode.EXCEPTION_ERROR_INFO);
			return result;
		}
		return result;
	}

	/**
	 * 取消支付流水
	 */
	@RequestMapping("/cancelBill")
	@ResponseBody
	public OperateResult cancelBill(HttpServletRequest request, String billNo) {

		Acount acount = cacheService.getAcountInfoFromCache(request);
		if (acount == null) {
			// TODO 没哟权限
			return new OperateResult(false, "请重新登陆");
		}
		OperateResult result = new OperateResult();
		try {
			PayBillRequest payBillRequest=new PayBillRequest();
			payBillRequest.setAcount(acount);
			payBillRequest.setBillNo(billNo);
			result = orderService.cancelBill(payBillRequest);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(ErrorCode.EXCEPTION_ERROR_INFO);
			return result;
		}
		return result;
	}

	/**
	 * 创建订单
	 * 
	 * @param request
//	 * @param productId
	 * @return
	 */
	@RequestMapping("/createOrder")
	@ResponseBody
	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
	public OperateResult createOrder(HttpServletRequest request, OrderBaseRequest baseRequest) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		if (acount == null) {
			// TODO 没哟权限
			return new OperateResult(false, "请重新登陆");
		}
		baseRequest.setAcount(acount);
		OperateResult result = new OperateResult();
		try {
			result = orderHelper.createOrder(baseRequest);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(ErrorCode.EXCEPTION_ERROR_INFO);
			return result;
		}

		return result;
	}

	/**
	 * 获取订单以及流水状态
	 * 
	 * @param request
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/getOrderAndBill")
	@ResponseBody
	public OperateResult getOrderAndBillStatus(HttpServletRequest request, String orderNo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		if (acount == null) {
			// TODO 没哟权限
			return new OperateResult(false, "请重新登陆");
		}
		try {
			OrderStatusInfo orderStatusInfo = orderService.queryOrderStatus(orderNo);
			return new OperateResult(true, "", orderStatusInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return new OperateResult(false, ErrorCode.EXCEPTION_ERROR_INFO);
		}

	}

	@RequestMapping("/cancelOrder")
	@ResponseBody
	public OperateResult cancelOrder(HttpServletRequest request, OrderBaseRequest baseRequest) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		if (acount == null) {
			// TODO 没哟权限
			return new OperateResult(false, "请重新登陆");
		}
		OperateResult result = new OperateResult();
		try {
			baseRequest.setAcount(acount);
			result = orderHelper.cancelOrder(baseRequest);
		} catch (Exception e) {
			result.setMessage(ErrorCode.EXCEPTION_ERROR_INFO);
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@RequestMapping("/getOrderList")
	@ResponseBody
	public OperateResult getOrderListByStatus(HttpServletRequest request, OrderBaseRequest orderRequest) {
		OperateResult result = new OperateResult();
		Acount acount = cacheService.getAcountInfoFromCache(request);
		if (acount == null) {
			// TODO 没哟权限
			return new OperateResult(false, "请重新登陆");
		}
		try {
			PageHelper.startPage(orderRequest.getCurPage(), orderRequest.getPageSize(), true);
			orderRequest.setAccountId(acount.getId() + "");
			Page<CarsokOrder> page = orderService.queryOrdersByStatus(orderRequest);
			result.setData(page.toPageInfo());
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(ErrorCode.EXCEPTION_ERROR_INFO);
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("getPayBill")
	@ResponseBody
	public OperateResult getPayBill(HttpServletRequest request, String billNo) {
		OperateResult result = new OperateResult();
		try {
			CarsokPayBill payBill = payBillService.queryPayBill(billNo);
			result.setSuccess(true);
			result.setData(payBill);
		} catch (Exception e) {
			result.setMessage(ErrorCode.EXCEPTION_ERROR_INFO);
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/getOrderCount")
	@ResponseBody
	public OperateResult getOrderCount(HttpServletRequest request,OrderBaseRequest baseRequest) {
		OperateResult result = new OperateResult();
		Acount acount = cacheService.getAcountInfoFromCache(request);
		if (acount == null) {
			// TODO 没哟权限
			return new OperateResult(false, "请重新登陆");
		}
		baseRequest.setAccountId(acount.getId()+"");
		try {
			Map<String, Object> map = orderService.queryOrderCount(baseRequest);
			result.setSuccess(true);
			result.setData(map);
		} catch (Exception e) {
			result.setMessage(ErrorCode.EXCEPTION_ERROR_INFO);
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/getDataOrderPhone")
	@ResponseBody
	public OperateResult getCacheOrder(OrderBaseRequest baseRequest){
		ValueOperations<String, String> valueOperations=redisTemplate.opsForValue();
		String value=valueOperations.get(baseRequest.getOrderNo());
		if(StringUtils.isEmpty(value)){
			return new OperateResult(false, "订单不存在");
		}
		else{
			return new OperateResult(true,"",value);
		}
	}
}
