package com.uton.carsokApi.model;

import java.io.Serializable;
import java.util.Date;

public class Banner implements Serializable{
	private static final long serialVersionUID = -8430182804083181155L;
	
	private Integer id;
	
	private String title;
	
	private String picurl;
	
	private String clickurl;
	
	private Integer sortnum;
	
	private Integer postype;
	
    private Date createTime;
    
    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getClickurl() {
		return clickurl;
	}

	public void setClickurl(String clickurl) {
		this.clickurl = clickurl;
	}

	public Integer getSortnum() {
		return sortnum;
	}

	public void setSortnum(Integer sortnum) {
		this.sortnum = sortnum;
	}

	public Integer getPostype() {
		return postype;
	}

	public void setPostype(Integer postype) {
		this.postype = postype;
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
