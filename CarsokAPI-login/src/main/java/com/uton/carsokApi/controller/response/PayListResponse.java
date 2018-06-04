package com.uton.carsokApi.controller.response;

import java.util.Date;
import java.util.List;

import com.uton.carsokApi.model.PosPayDetail;

public class PayListResponse {
	private Integer id;
	private Integer product_id;
	private String product_imgurl;
	private String product_name;
	private String order_price;
	private String pay_price;
	private String nopay_price;
	private String order_num;
	private String payBarCodeUrl;
	private Date pay_time;
	private String pos_id;
	private List<PosPayDetail> payDetails;  //支付明细
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_imgurl() {
		return product_imgurl;
	}
	public void setProduct_imgurl(String product_imgurl) {
		this.product_imgurl = product_imgurl;
	}
	public String getOrder_price() {
		return order_price;
	}
	public void setOrder_price(String order_price) {
		this.order_price = order_price;
	}
	public String getPay_price() {
		return pay_price;
	}
	public void setPay_price(String pay_price) {
		this.pay_price = pay_price;
	}
	public String getNopay_price() {
		return nopay_price;
	}
	public void setNopay_price(String nopay_price) {
		this.nopay_price = nopay_price;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public String getPayBarCodeUrl() {
		return payBarCodeUrl;
	}
	public void setPayBarCodeUrl(String payBarCodeUrl) {
		this.payBarCodeUrl = payBarCodeUrl;
	}
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	public String getPos_id() {
		return pos_id;
	}
	public void setPos_id(String pos_id) {
		this.pos_id = pos_id;
	}
	public List<PosPayDetail> getPayDetails() {
		return payDetails;
	}
	public void setPayDetails(List<PosPayDetail> payDetails) {
		this.payDetails = payDetails;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
}
