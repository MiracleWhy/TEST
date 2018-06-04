package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.controller.request.Page;

public class ContractResponse extends Page{

	private int id;
	private String url;
	private String carId;
	private String contract_num;
	private int typeInt;//合同类型
	private String type;//合同类型字符
	private String name;//对应合同名称

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContract_num() {
		return contract_num;
	}
	public void setContract_num(String contract_num) {
		this.contract_num = contract_num;
	}
	public int getTypeInt() {
		return typeInt;
	}
	public void setTypeInt(int typeInt) {
		this.typeInt = typeInt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
