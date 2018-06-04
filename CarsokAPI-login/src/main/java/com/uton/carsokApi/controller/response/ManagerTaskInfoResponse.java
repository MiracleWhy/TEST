package com.uton.carsokApi.controller.response;

import java.util.List;

import com.uton.carsokApi.model.ZbMoneyInfo;
import com.uton.carsokApi.model.ZbTaskManager;

public class ManagerTaskInfoResponse {
	private ZbTaskManager managerInfo;
	private List<ZbMoneyInfo> mlist;
	public ZbTaskManager getManagerInfo() {
		return managerInfo;
	}
	public void setManagerInfo(ZbTaskManager managerInfo) {
		this.managerInfo = managerInfo;
	}
	public List<ZbMoneyInfo> getMlist() {
		return mlist;
	}
	public void setMlist(List<ZbMoneyInfo> mlist) {
		this.mlist = mlist;
	}
	
}
