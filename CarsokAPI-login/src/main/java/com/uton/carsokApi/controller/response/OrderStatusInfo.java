package com.uton.carsokApi.controller.response;

import java.util.List;

import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.CarsokOrderDetail;
import com.uton.carsokApi.model.CarsokPayBill;

public class OrderStatusInfo {
	// 订单信息
	// 支付信息
	private CarsokOrder order;

	private List<CarsokPayBill> payBills;

	private List<CarsokOrderDetail> orderDetails;

	public CarsokOrder getOrder() {
		return order;
	}

	public void setOrder(CarsokOrder order) {
		this.order = order;
	}

	public List<CarsokPayBill> getPayBills() {
		return payBills;
	}

	public void setPayBills(List<CarsokPayBill> payBills) {
		this.payBills = payBills;
	}

	public List<CarsokOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<CarsokOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
