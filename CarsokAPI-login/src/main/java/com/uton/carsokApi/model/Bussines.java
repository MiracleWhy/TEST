package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @describe:市场拓展管理实体类
 * @author:zhy
 * @cratetime:20170628
 */

public class Bussines {
		//id
		private Integer id;
	    //车行名称
		private String carIndustry;
		//再次拜访时间
		private Date revisit;
		//负责人
		private String personInCharge;
		//地址
		private String address; 
		//电话
		private String telephone;
		//首次拜访时间
		private Date firstVisittime;
		//车行规模
		private String vehicleScale;
		//兴趣级别
		private String interestLevel;
		//员工人数
		private String empnumber;
		//购买预算
	    private String purchaseBudget;
		//以前使用的saas平台
	    private String saas;
		//门头照
	    private String shopboorPicture;
		//是否成交
	    private String isdealok;
		//保存日期
	    private Date createTime;
		//修改时间
	    private Date updateTime;
		//填写人id
	    private Integer acountId;
		//备注
	    private String remarks;
	    //子账号
	    private Integer chilAcountId;
	    //登录电话
	    private String phoe;
	    //是否添加子账号
		private String isAddChild;
		//是否录入商户信息
		private  String isCommercialMsg;
		//是否安装pos机
	    private  String isPos;
	    //已使用车商APP功能;
		private  String carsokApiFunction;
		//是否使用tps
		private  String isTps;

		private String creater;

		//地區字段
	    private String region;
	    //當前所屬人
	private  String nowCreater;

	public String getNowCreater() {
		return nowCreater;
	}

	public void setNowCreater(String nowCreater) {
		this.nowCreater = nowCreater;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setIsAddChild(String isAddChild) {
		this.isAddChild = isAddChild;
	}

	public void setIsCommercialMsg(String isCommercialMsg) {
		this.isCommercialMsg = isCommercialMsg;
	}

	public void setIsPos(String isPos) {
		this.isPos = isPos;
	}

	public void setCarsokApiFunction(String carsokApiFunction) {
		this.carsokApiFunction = carsokApiFunction;
	}

	public void setIsTps(String isTps) {
		this.isTps = isTps;
	}

	public String getIsAddChild() {
		return isAddChild;
	}

	public String getIsCommercialMsg() {
		return isCommercialMsg;
	}

	public String getIsPos() {
		return isPos;
	}

	public String getCarsokApiFunction() {
		return carsokApiFunction;
	}

	public String getIsTps() {
		return isTps;
	}

	public String getPhoe() {
			return phoe;
		}
		public void setPhoe(String phoe) {
			this.phoe = phoe;
		}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	private Integer childId;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getCarIndustry() {
			return carIndustry;
		}
		public void setCarIndustry(String carIndustry) {
			this.carIndustry = carIndustry;
		}

	public Date getRevisit() {
		return revisit;
	}

	public void setRevisit(Date revisit) {
		this.revisit = revisit;
	}

	public String getPersonInCharge() {
			return personInCharge;

		}
		public void setPersonInCharge(String personInCharge) {
			this.personInCharge = personInCharge;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public Date getFirstVisittime() {
			return firstVisittime;
		}
		public void setFirstVisittime(Date firstVisittime) {
			this.firstVisittime = firstVisittime;
		}

	public String getVehicleScale() {
		return vehicleScale;
	}

	public void setVehicleScale(String vehicleScale) {
		this.vehicleScale = vehicleScale;
	}

	public String getInterestLevel() {
			return interestLevel;
		}
		public void setInterestLevel(String interestLevel) {
			this.interestLevel = interestLevel;
		}
		public String getEmpnumber() {
			return empnumber;
		}
		public void setEmpnumber(String empnumber) {
			this.empnumber = empnumber;
		}
		public String getSaas() {
			return saas;
		}
		public void setSaas(String saas) {
			this.saas = saas;
		}
		public String getShopboorPicture() {
			return shopboorPicture;
		}
		public void setShopboorPicture(String shopboorPicture) {
			this.shopboorPicture = shopboorPicture;
		}
		public String getIsdealok() {
			return isdealok;
		}
		public void setIsdealok(String isdealok) {
			this.isdealok = isdealok;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public Integer getAcountId() {
			return acountId;
		}
		public void setAcountId(Integer acountId) {
			this.acountId = acountId;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public Integer getChilAcountId() {
			return chilAcountId;
		}
		public void setChilAcountId(Integer chilAcountId) {
			this.chilAcountId = chilAcountId;
		}

	public String getPurchaseBudget() {
		return purchaseBudget;
	}

	public void setPurchaseBudget(String purchaseBudget) {
		this.purchaseBudget = purchaseBudget;
	}
}
