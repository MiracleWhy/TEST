package com.uton.carsokApi.controller.request;

import java.math.BigDecimal;
import java.util.List;

import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.pay.lakala.model.OrderStatus;

public class OrderBaseRequest extends Page {
	private Acount acount;

	private String productId;

	private OrderTypeEnum type;

	private String orderNo;

	private String billNo;
	
	private OrderStatus orderStatus;
	
	private String searchKey;

	private String accountId;
	
	private List<OrderTypeEnum> orderTypes;
	
	private BigDecimal totalMoney;
	/**
	 * 收费项目名称
	 * @return
	 */
	private String productName;
	/**
	 * 销售人员姓名
	 */
	private String salesmanName;
	/**
	 * 备注
	 */
	private String remark;
	
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public List<OrderTypeEnum> getOrderTypes() {
		return orderTypes;
	}

	public void setOrderTypes(List<OrderTypeEnum> orderTypes) {
		this.orderTypes = orderTypes;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Acount getAcount() {
		return acount;
	}

	public void setAcount(Acount acount) {
		this.acount = acount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public OrderTypeEnum getType() {
		return type;
	}

	public void setType(OrderTypeEnum type) {
		this.type = type;
	}



}
