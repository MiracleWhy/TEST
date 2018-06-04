package com.uton.carsokApi.controller.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskInfoResponse {
	private int id;
	private String carName;
	private String carNum;
	private String lastCarNum;
	private String taskNum;
	private Date taskTime;
	private String remark;
	private Object taskInfo;
	private String vin;
	private Map<String,String> picMap;//图片地址
	private List<Object> againList;
	private Integer againTimes;

	public Date getAgainTime() {
		return againTime;
	}

	public void setAgainTime(Date againTime) {
		this.againTime = againTime;
	}

	private Date againTime;

	public Integer getAgainTimes() {
		return againTimes;
	}

	public void setAgainTimes(Integer againTimes) {
		this.againTimes = againTimes;
	}

	public List<Object> getAgainList() {
		return againList;
	}

	public void setAgainList(List<Object> againList) {
		this.againList = againList;
	}

	public Map<String, String> getPicMap() {
		return picMap;
	}
	public void setPicMap(Map<String, String> picMap) {
		this.picMap = picMap;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}

	public Date getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}
	public Object getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(Object taskInfo) {
		this.taskInfo = taskInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLastCarNum() {
		return lastCarNum;
	}
	public void setLastCarNum(String lastCarNum) {
		this.lastCarNum = lastCarNum;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
}
