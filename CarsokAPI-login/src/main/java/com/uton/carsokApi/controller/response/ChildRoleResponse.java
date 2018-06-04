package com.uton.carsokApi.controller.response;

import java.util.List;

import com.uton.carsokApi.model.ChildRoleVo;

public class ChildRoleResponse {
	private String roleName;
	private String roleKey;
	private List<ChildRoleVo> data;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	public List<ChildRoleVo> getData() {
		return data;
	}
	public void setData(List<ChildRoleVo> data) {
		this.data = data;
	}
	
	
}
