package com.uton.carsokApi.model;

import java.util.Date;

public class TaskVo {
	private int id;
	private String carName;
	private String carNum;
	private int taskStatus;
	private String taskNum;
	private String taskAccount;
	private Date taskTime;
	private String vin;
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
	public int getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}
	public String getTaskAccount() {
		return taskAccount;
	}
	public void setTaskAccount(String taskAccount) {
		this.taskAccount = taskAccount;
	}
	public Date getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}
	public String getVin() { return vin; }
	public void setVin(String vin) { this.vin = vin; }
}
