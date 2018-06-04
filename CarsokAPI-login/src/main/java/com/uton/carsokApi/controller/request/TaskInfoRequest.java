package com.uton.carsokApi.controller.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class TaskInfoRequest {
	@NotNull(message="taskid is not null")
	private int taskid;
	
	@NotEmpty(message="roleName is not null")
	private String roleName;

	public int getTaskid() {
		return taskid;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
