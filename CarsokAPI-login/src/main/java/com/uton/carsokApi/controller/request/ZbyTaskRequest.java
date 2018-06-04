package com.uton.carsokApi.controller.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.uton.carsokApi.model.TaskZbBill;
import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.uton.carsokApi.model.ZbMoneyInfo;

public class ZbyTaskRequest {
	@NotNull(message="tid is not null")
	private int tid;
	
	private String zbMoney;

	private String vin;
	
	private String remark;
	
	private List<ZbMoneyInfo> minfos;
	
	//@NotEmpty(message="lastCarNum is not null")
	private String lastCarNum;

	private List<String> billList;

	public List<String> getBillList() {
		return billList;
	}

	public void setBillList(List<String> billList) {
		this.billList = billList;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getZbMoney() {
		return zbMoney;
	}

	public void setZbMoney(String zbMoney) {
		this.zbMoney = zbMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ZbMoneyInfo> getMinfos() {
		return minfos;
	}

	public void setMinfos(List<ZbMoneyInfo> minfos) {
		this.minfos = minfos;
	}

	public String getLastCarNum() {
		return lastCarNum;
	}

	public void setLastCarNum(String lastCarNum) {
		this.lastCarNum = lastCarNum;
	}
	
/*	public static void main(String[] args) {
		ZbMoneyInfo info = new ZbMoneyInfo();
		info.setDetail("修保险杠");
		info.setAmount(new BigDecimal(100));
		ZbMoneyInfo info1 = new ZbMoneyInfo();
		info1.setDetail("修保险杠1");
		info1.setAmount(new BigDecimal(100));
		ZbMoneyInfo info2 = new ZbMoneyInfo();
		info2.setDetail("修保险杠1");
		info2.setAmount(new BigDecimal(100));
		List<ZbMoneyInfo> data = new ArrayList<ZbMoneyInfo>();
		data.add(info);
		data.add(info1);
		data.add(info2);
		ZbyTaskRequest req = new ZbyTaskRequest();
		req.setMinfos(data);
		req.setTid(1);
		req.setZbMoney(new BigDecimal(300));
		req.setRemark("sdfasdf");
		JSONObject json = new JSONObject();
		System.out.println(json.toJSONString(req));
	}*/
	
}
