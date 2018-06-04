package com.uton.carsokApi.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.controller.response.OrderStatusInfo;
import com.uton.carsokApi.dao.AccountLklMapper;
import com.uton.carsokApi.dao.CarsokOrderDetailMapper;
import com.uton.carsokApi.dao.CarsokOrderMapper;
import com.uton.carsokApi.dao.CarsokPayBillMapper;
import com.uton.carsokApi.dao.NoticeMapper;
import com.uton.carsokApi.dao.PictureMapper;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.dao.PruductOldcarMapper;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.AccountLkl;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.CarsokOrderDetail;
import com.uton.carsokApi.model.CarsokPayBill;
import com.uton.carsokApi.model.Notice;
import com.uton.carsokApi.model.Picture;
import com.uton.carsokApi.model.Product;
import com.uton.carsokApi.model.PruductOldcar;
import com.uton.carsokApi.pay.lakala.model.OrderStatus;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;
import com.uton.carsokApi.pay.lakala.model.PayStatus;
import com.uton.carsokApi.pay.lakala.model.ProductStatus;
import com.uton.carsokApi.pay.lakala.util.SignUtils;
import com.uton.carsokApi.service.OrderService;
import com.uton.carsokApi.service.PayBillService;
import com.uton.carsokApi.service.SubUserService;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;

@Service
public class OrderServiceImpl implements OrderService {

	public static Logger logger = LoggerFactory.getLogger(OrderService.class);
	@Autowired
	private CarsokOrderMapper orderMapper;
	@Autowired
	private CarsokOrderDetailMapper orderDetailMapper;
	@Autowired
	private CarsokPayBillMapper payBillMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private PayBillService payBillService;
	@Autowired
	private PictureMapper pictureMapper;
	@Autowired
	private EventBus eventBus;
	@Value("${producturl.prefix}")
	private String producturl_prefix;
	@Autowired
	private AccountLklMapper accountLklMapper;
	@Autowired
	private PruductOldcarMapper pruductOldcarMapper;
	@Autowired
	private SubUserService subUserService;
	/**
	 * 创建订单<br>
	 * 参数：订单信息 返回值：成功or失败，订单编号，以及加密信息。 一次收款一个商品
	 */
	public OperateResult createOrder(Product product, Acount acount) {
		// 创建订单
		OperateResult result = new OperateResult();
		CarsokOrder order = new CarsokOrder();
		try {
			AccountLkl accountLkl = accountLklMapper.selectByAccountIdForUpdate(acount.getAccount());
			if(accountLkl==null){
				return new OperateResult(false, "用户未开通拉卡拉");
			}
			// 创建订单
			String orderNo = SignUtils.getOrderNo();
			order.setOrderNo(orderNo);
			order.setBizId(orderNo);
			order.setAccountId(acount.getId()+"");
			order.setRestMoney(product.getPrice());
			order.setTotalMoney(product.getPrice());
			order.setType(OrderTypeEnum.CAR);
			order.setAccountRealName(accountLkl.getRealName());
			// 查询图片
			Picture pictureQuery = new Picture();
			pictureQuery.setProductId(product.getId());
			short pictype = 1;
			pictureQuery.setType(pictype); // 只查询主图
			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
			String img = pictureList.size() > 0 ? pictureList.get(0).getPicPath() : "";
			String miles = "0";
			String carDate="1970-01-01";
			String mobile = StringUtil.isEmpty(acount.getSubPhone()) ? acount.getAccount() : acount.getSubPhone();
			//description 信息
			PruductOldcar pruductOldcar=new PruductOldcar();
			pruductOldcar.setPruductId(product.getId());
			pruductOldcar=pruductOldcarMapper.selectByModel(pruductOldcar);
			if(pruductOldcar!=null){
				miles=pruductOldcar.getMiles()+"";
				carDate=DateUtil.DateToString(pruductOldcar.getFirstUpTime(), "yyyy-MM-dd");
			}
			JSONObject description = new JSONObject();
			description.put("productName", product.getProductShortName());
			description.put("img", img);
			description.put("price", product.getPrice().intValue());
			description.put("productUrl", producturl_prefix + product.getProductNo() + ".html?phoneNumber=" + mobile);
			description.put("productId",product.getId());
			description.put("miles", miles);
			description.put("carDate", carDate);
			description.put("creator", mobile);
			order.setDescription(description.toJSONString());
			result = create(order);
			order = (CarsokOrder) result.getData();
			// 创建订单详情
			CarsokOrderDetail orderDetail = new CarsokOrderDetail();
			orderDetail.setProductCount(1);
			orderDetail.setProductId(product.getId() + "");
			orderDetail.setProductPrice(product.getPrice());
			orderDetail.setStatus(ProductStatus.FREEZE.name());
			orderDetail.setOrderNo(orderNo);
			orderDetail.setGmtModify(new Date());
			orderDetailMapper.insert(orderDetail);
			// 修改商品信息
			//2017/06/14 先已售再支付 这里注销掉
//			product.setSaleStatus((short) 1);
//			product.setUpdateTime(new Date());
//			productMapper.updateByModel(product);
//			//商品售出
//			Notice notice = new Notice("carsok_product", product.getId(), 2);
//			noticeMapper.insert(notice);
		} catch (Exception e) {
			result.setMessage("订单创建失败");
			result.setSuccess(false);
			return result;
		}
		result.setSuccess(true);
		result.setData(order);
		return result;
	};

	/**
	 * 修改订单<br>
	 * 参数： 返回值：接收通知
	 */
	public OperateResult modifyOrder(CarsokOrder order) {
		if (order == null) {
			return new OperateResult(false, "订单不存在");
		}
		order.setGmtModify(new Date());
		try {
			orderMapper.updateByPrimaryKeySelective(order);
		} catch (Exception e) {
			logger.error("修改订单失败:" + e.getMessage() + ",订单信息:" + JSONObject.toJSONString(order));
			new OperateResult(false, "修改订单失败,请重试。");
		}
		return new OperateResult(true, "");
	};

	public CarsokOrder queryByOrderNoForUpdate(String orderNo) {
		return orderMapper.selectByOrderNoForUpdate(orderNo);
	}

	public OrderStatusInfo queryOrderStatus(String orderNo) {
		OrderStatusInfo orderInfo = new OrderStatusInfo();
		CarsokOrder order = orderMapper.selectOrderStatus(orderNo);
		List<CarsokOrderDetail> orderDetails = orderDetailMapper.selectOrderDetails(orderNo);
		List<CarsokPayBill> payBills = payBillMapper.selectOrderBills(orderNo);
		orderInfo.setOrder(order);
		orderInfo.setOrderDetails(orderDetails);
		orderInfo.setPayBills(payBills);
		return orderInfo;
	}

	public OperateResult cancelOrder(OrderBaseRequest request) {
		CarsokOrder order = orderMapper.selectByOrderNoForUpdate(request.getOrderNo());
		order.setOrderStatus(OrderStatus.ALREADY_CANCLE.name());
		order.setPayStatus(PayStatus.CANCEL_PAY.name());
		order.setMemo("用户取消");
		return modifyOrder(order);
	}

	public CarsokOrder queryByOrderNo(String orderNo) {
		return orderMapper.selectByOrderNo(orderNo);
	}

	@Override
	public OperateResult createOrder(OrderBaseRequest request) {
		OperateResult result = new OperateResult();
		Acount acount = request.getAcount();
		Product product = productMapper.selectByIdForUpdate(request.getProductId(), acount.getId());
		if (product.getOnShelfStatus() != 1 || product.getSaleStatus() == 1 || product.getIsDel() == 1) {
			result.setMessage("商品状态错误,请返回重新下单。");
			return result;
		}
		result = this.createOrder(product,
				acount);/**
						 * 创建订单，并生成订单加密信息 productId 生成订单
						 */
		return result;
	}

	@Override
	public OperateResult createBill(PayBillRequest request) {
		logger.info("创建流水请求信息:" + JSONObject.toJSONString(request));
		OperateResult result = checkBill(request.getMoney());
		if (!result.isSuccess()) {
			return result;
		}
		Acount acount=request.getAcount();
		String subPhone=acount.getSubPhone();
		if(!StringUtils.isEmpty(subPhone)&&!subUserService.querySubUserRoles(acount).contains("cwzy")){
			return new OperateResult(false, "没有权限");
		}
		CarsokOrder order = queryByOrderNoForUpdate(request.getOrderNo());
		if (order == null) {
			logger.error("获取订单信息失败");
			return result;
		}
		// 验证金额是否正确
		result = checkBill(order.getRestMoney(), request.getMoney());
		logger.info("创建流水检查结果:" + JSONObject.toJSONString(result));
		if (!result.isSuccess()) {
			logger.error("创建流水检查失败");
			return result;
		}
		CarsokPayBill payBill = new CarsokPayBill();
			// 修改订单
			// 创建bill
			// 返回bill加密信息
			order.setRestMoney(order.getRestMoney().subtract(request.getMoney()));
			result = modifyOrder(order);
			payBill.setOrderNo(order.getOrderNo());
			payBill.setBillMoney(request.getMoney());
			payBill.setBody(order.getType().message());
			payBill.setAccountRealName(StringUtils.isEmpty(request.getAccountRealName())?order.getAccountRealName():request.getAccountRealName());
			payBill.setMemo(request.getMemo());
			payBill.setAccountId(request.getAcount().getAccount());
			JSONObject description=new JSONObject();
			description.put("creator", StringUtils.isEmpty(subPhone)?acount.getAccount():subPhone);
			payBill.setDescription(description.toJSONString());
			result = payBillService.createBill(payBill);
			BaseEvent newEvent = new BaseEvent();
			newEvent.setData(payBill.getOrderNo());
			newEvent.setEventName(EventConstants.ORDER_STATUS_CHECK_EVENT);
			newEvent.setWeight("1000");
			eventBus.publish(newEvent);
			return result;

	}

	@Override
	@Transactional
	public OperateResult cancelBill(PayBillRequest request) {
		logger.info("取消流水支付操作开始，billNo：" + request.getBillNo());
		String billNo=request.getBillNo();
		OperateResult result = new OperateResult();
		Acount acount=request.getAcount();
		String subPhone=acount.getSubPhone();
		if(!StringUtils.isEmpty(subPhone)&&!subUserService.querySubUserRoles(acount).contains("cwzy")){
			return new OperateResult(false, "没有权限");
		}
		CarsokPayBill payBill = payBillService.queryPayBillForUpdate(billNo);
		if (payBill == null) {
			result.setMessage("操作失败，流水状态错误，请重试。");
			logger.error("取消支付流水发生异常:billNo" + billNo + ",errorMessage:流水状态错误");
			return result;
		}
		if (!StringUtils.equals(PayStatus.WAIT_PAY.name(), payBill.getBillStatus())) {
			result.setMessage("操作失败，流水状态错误，当前状态为:" + Enum.valueOf(PayStatus.class, payBill.getBillStatus()));
			logger.error("取消支付流水发生异常:billNo" + billNo + ",errorMessage:流水不是未支付状态");
			return result;
		}
		String orderNo = payBill.getOrderNo();
		CarsokOrder order = queryByOrderNoForUpdate(orderNo);
		if (order == null) {
			result.setMessage("操作失败，订单状态错误，请重试。");
			logger.error("取消支付流水发生异常:billNo" + billNo + ",orderNo" + orderNo + ",errorMessage:订单状态错误");
			return result;
		}
		try {
			payBill.setBillStatus(PayStatus.CANCEL_PAY.name());
			payBill.setMemo("用户取消");
			payBill.setEnable(false);
			JSONObject description=JSONObject.parseObject(payBill.getDescription());
			if(description==null){
				description=new JSONObject();
			}
			description.put("canclor", StringUtils.isEmpty(subPhone)?acount.getAccount():subPhone);
			payBill.setDescription(description.toJSONString());
			BigDecimal restMoney = order.getRestMoney();
			order.setRestMoney(restMoney.add(payBill.getBillMoney()));
			order.setGmtModify(new Date());
			modifyOrder(order);
			payBillService.modifyBill(payBill);
			BaseEvent newEvent = new BaseEvent();
			newEvent.setData(payBill.getOrderNo());
			newEvent.setEventName(EventConstants.ORDER_STATUS_CHECK_EVENT);
			newEvent.setWeight("1000");
			eventBus.publish(newEvent);
			// 操作结束，发送订单金额check事件. TODO
		} catch (Exception e) {
			logger.error("取消支付流水发生异常:billNo" + billNo + ",errorMessage:" + e.getMessage());
			result.setMessage("操作失败，请稍后重试。");
			return result;
		}
		logger.info("取消流水支付操作成功，billNo：" + billNo);
		// 需要进行订单金额修改
		result.setSuccess(true);
		return result;
	}

	@Override
	public OperateResult checkBill(BigDecimal restMoney, BigDecimal money) {
		OperateResult result = new OperateResult();
		if (restMoney == null) {
			result.setMessage("订单未支付金额错误");
			return result;
		}
		if (money.compareTo(restMoney) > 0) {
			result.setMessage("支付金额不能大于剩余金额");
			return result;

		}
		result.setSuccess(true);
		return result;
	}

	@Override
	public OperateResult checkBill(BigDecimal money) {
		if (money.compareTo(new BigDecimal(50 * 10000)) > 0) {
			return new OperateResult(false, "超过限额");
		} else if (money.compareTo(new BigDecimal("0.01")) < 0) {
			return new OperateResult(false, "金额不能小于0.01元");
		}
		return new OperateResult(true, "");
	}

	@Override
	public Page<CarsokOrder> queryOrdersByStatus(OrderBaseRequest orderBaseRequest) {

		return orderMapper.selectOrdersByStatus(orderBaseRequest);
	}

	@Override
	public OperateResult create(CarsokOrder order) {
		order.setPayMoney(new BigDecimal(0));
		order.setEnable(true);
		order.setGmtExpire(new Date(System.currentTimeMillis() + 60 * 1000));// TODO
		order.setGmtCreate(new Date());
		order.setGmtModify(new Date());
		order.setRestMoney(order.getTotalMoney());
		order.setOrderStatus(OrderStatus.ALREADY_CONFIRM.name());
		order.setPayStatus(PayStatus.WAIT_PAY.name());
		orderMapper.insert(order);
		return new OperateResult(true, "", order);
	}

	@Override
	public List<String> queryProductIdsByCondition(CarsokOrder order) {
		return orderMapper.selectProductIdsByCondition(order);
	}

	@Override
	public Map<String, Object> queryOrderCount(OrderBaseRequest baseRequests) {
		List<Map <String,Object>> maps=orderMapper.selectOrderCount(baseRequests);
		Map<String,Object>  orderCountMap=new HashMap<String,Object>();
		List<OrderStatus> orderStatus=Arrays.asList(OrderStatus.values());
		for(OrderStatus status :orderStatus){
			orderCountMap.put(status.name(), 0);
		}
		long total=0;
		for(Map map:maps){
			for(OrderStatus status :orderStatus){
				if(StringUtils.equals(status.name(), map.get("orderStatus").toString())){
					orderCountMap.put(status.name(), map.get("counts"));
					total=total+(Long)map.get("counts");
				}
			}
		}
		orderCountMap.put("totalCounts", total);
		return orderCountMap;
	}

	@Override
	public CarsokOrder queryTestOrderForUpdate() {
		
		return orderMapper.selectTestOrderForUpdate();
	}

	@Override
	public List<Map<String,String>> querByProductIdsAndType(List<String> ids, OrderTypeEnum car) {
		
		return orderMapper.selectByProductIdsAndType(ids,car);
	}
}
