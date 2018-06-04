package com.uton.carsokApi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.controller.request.OrderBaseRequest;
import com.uton.carsokApi.controller.response.OrderStatusInfo;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.Product;
import com.uton.carsokApi.pay.lakala.model.PayBillRequest;

/**
 * 订单服务
 * 
 * @author jml
 *
 *         2017年3月13日
 */
public interface OrderService {
	
	public OperateResult create(CarsokOrder order);
	/**
	 * 创建订单
	 */
	public OperateResult createOrder(Product product, Acount acount);
	/**
	 * 修改订单
	 */
	public OperateResult modifyOrder(CarsokOrder order);
	/**
	 * 锁定订单 
	 */
	public CarsokOrder queryByOrderNoForUpdate(String orderNo);
	/**
	 * 查询订单详情
	 */
	public OrderStatusInfo queryOrderStatus(String orderNo);
	/**
	 * 取消订单
	 */
	public OperateResult cancelOrder(OrderBaseRequest request);
	/**
	 * 查询订单	
	 */
	public CarsokOrder queryByOrderNo(String orderNo);
	/**
	 * 创建订单
	 */
	public OperateResult createOrder(OrderBaseRequest request);
	/**
	 * 创建bill
	 */
	public OperateResult createBill(PayBillRequest request);
	/**
	 * 取消bill
	 */
	public OperateResult cancelBill(PayBillRequest request) ;
	/**
	 * 检查流水
	 */
	public OperateResult checkBill(BigDecimal restMoney, BigDecimal money);
	/**
	 * 检查流水
	 */
	public OperateResult checkBill(BigDecimal money);
	/**
	 * 查询订单列表
	 */
	public Page<CarsokOrder> queryOrdersByStatus(OrderBaseRequest orderRequest);
	/**
	 * 根据条件查询订单
	 */
	public List<String> queryProductIdsByCondition(CarsokOrder order);
	/**
	 *  查询订单数量
	 */
	public Map<String, Object> queryOrderCount(OrderBaseRequest baseRequest);
	/**
	 * 
	 * @return
	 */
	public CarsokOrder queryTestOrderForUpdate();
	
	
	public List<Map<String,String>> querByProductIdsAndType(List<String> ids, OrderTypeEnum car);
}
