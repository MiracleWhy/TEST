package com.uton.carsokApi.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

public class ToDoTaskRequest {
	@NotEmpty(message="roleName is not null")
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
