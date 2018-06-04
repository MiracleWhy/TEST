package com.uton.carsokApi.model;

import java.io.Serializable;
import java.util.Date;

public class Categorie implements Serializable{
	private static final long serialVersionUID = -3105272105665824892L;
	
	private Integer id;
	
	private Integer parentid;
	
	private String name;
	
	private String fullname;
	
	private String initial;
	
	private Integer depth;
	
	private String logo;
	
	private String price;
	
	private String yeartype;
	
	private String salestate;
	
	private String productionstate;
	
	private String sizetype;
	
    private Date createTime;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getYeartype() {
		return yeartype;
	}

	public void setYeartype(String yeartype) {
		this.yeartype = yeartype;
	}

	public String getSalestate() {
		return salestate;
	}

	public void setSalestate(String salestate) {
		this.salestate = salestate;
	}

	public String getProductionstate() {
		return productionstate;
	}

	public void setProductionstate(String productionstate) {
		this.productionstate = productionstate;
	}

	public String getSizetype() {
		return sizetype;
	}

	public void setSizetype(String sizetype) {
		this.sizetype = sizetype;
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
    
    
}
