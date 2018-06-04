package com.uton.carsokApi.pay.lakala.model;

import java.util.List;

import com.uton.carsokApi.model.CarsokOrder;
import com.uton.carsokApi.model.CarsokPayBill;

public class OrderPayInfo {

	private CarsokOrder order;

	private List<CarsokPayBill> payBills;

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

}
