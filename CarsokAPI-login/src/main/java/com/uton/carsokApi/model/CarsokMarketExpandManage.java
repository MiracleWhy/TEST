package com.uton.carsokApi.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @describe:市场拓展管理实体类
 * @author:zhy
 * @cratetime:20170628
 */
public class CarsokMarketExpandManage {
		//id
		private Integer id;
	    //车行名称
		private String carIndustry;
		//再次拜访时间
		private Date revisit;
		//负责人
		private String person_in_charge;
		//地址
		private String address; 
		//电话
		private String telephone;
		//首次拜访时间
		private Date firstVisittime;
		//车行规模
		private Date vehicleScale;
		//兴趣级别
		private String interestLevel;
		//员工人数
		private String empnumber;
		//购买预算
	    private BigDecimal purchaseBudget;
		//以前使用的saas平台
	    private String saas;
		//门头照
	    private String shopboorPicture;
		//是否成交
	    private int isdealok;
		//成交日期
	    private Date dealtime;
		//保存日期
	    private Date createTime;
		//修改时间
	    private Date updateTime;
		//填写人id
	    private Integer acountId;
		//备注
	    private String remarks;
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
		public String getPerson_in_charge() {
			return person_in_charge;
		}
		public void setPerson_in_charge(String person_in_charge) {
			this.person_in_charge = person_in_charge;
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
		public Date getVehicleScale() {
			return vehicleScale;
		}
		public void setVehicleScale(Date vehicleScale) {
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
		public BigDecimal getPurchaseBudget() {
			return purchaseBudget;
		}
		public void setPurchaseBudget(BigDecimal purchaseBudget) {
			this.purchaseBudget = purchaseBudget;
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
		public int getIsdealok() {
			return isdealok;
		}
		public void setIsdealok(int isdealok) {
			this.isdealok = isdealok;
		}
		public Date getDealtime() {
			return dealtime;
		}
		public void setDealtime(Date dealtime) {
			this.dealtime = dealtime;
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
}
