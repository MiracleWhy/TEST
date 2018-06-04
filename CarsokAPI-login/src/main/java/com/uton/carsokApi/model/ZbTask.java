package com.uton.carsokApi.model;

import java.util.Date;

public class ZbTask {
	private int id;
	private String carName;
	private String carNum;
	private String lastCarNum;
	private int taskStatus;
	private String taskNum;
	private String taskAccount;
	private Date taskTime;
	private String vin;
	private int productId;
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
	public Date getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}
	public String getTaskAccount() {
		return taskAccount;
	}
	public void setTaskAccount(String taskAccount) {
		this.taskAccount = taskAccount;
	}
	public String getLastCarNum() {
		return lastCarNum;
	}
	public void setLastCarNum(String lastCarNum) {
		this.lastCarNum = lastCarNum;
	}
	public String getVin() { return vin; }
	public void setVin(String vin) { this.vin = vin; }
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

}
