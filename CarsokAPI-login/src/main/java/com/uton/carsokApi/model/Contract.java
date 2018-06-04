package com.uton.carsokApi.model;

public class Contract {
	private int id;
	private String contract_url;
	private String contract_num;
	private int car_id;
	private int contract_type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContract_url() {
		return contract_url;
	}
	public void setContract_url(String contract_url) {
		this.contract_url = contract_url;
	}
	public String getContract_num() {
		return contract_num;
	}
	public void setContract_num(String contract_num) {
		this.contract_num = contract_num;
	}
	public int getCar_id() {
		return car_id;
	}
	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}
	public int getContract_type() {
		return contract_type;
	}
	public void setContract_type(int contract_type) {
		this.contract_type = contract_type;
	}
	
}	
