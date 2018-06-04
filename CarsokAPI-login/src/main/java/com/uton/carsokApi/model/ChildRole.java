package com.uton.carsokApi.model;

import java.util.List;

public class ChildRole {
	private int id;
	private int childId;
	private String childList;
	private String roleName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChildId() {
		return childId;
	}
	public void setChildId(int childId) {
		this.childId = childId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getChildList() {
		return childList;
	}

	public void setChildList(String childList) {
		this.childList = childList;
	}
}
