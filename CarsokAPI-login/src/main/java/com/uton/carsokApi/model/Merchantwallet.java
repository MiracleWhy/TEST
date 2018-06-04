package com.uton.carsokApi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Merchantwallet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4855965448135717628L;
	
	
	private Integer id;
	
	private Integer accountid;
	
	private BigDecimal avail;
	
	private BigDecimal frozen;
	
    private Date createTime;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountid() {
		return accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}

	public BigDecimal getAvail() {
		return avail;
	}

	public void setAvail(BigDecimal avail) {
		this.avail = avail;
	}

	public BigDecimal getFrozen() {
		return frozen;
	}

	public void setFrozen(BigDecimal frozen) {
		this.frozen = frozen;
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
