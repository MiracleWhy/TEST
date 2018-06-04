package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
@TableName("carsok_authoritymanage")
public class CarsokAuthoritymanage extends Model<CarsokAuthoritymanage> {

    private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 数据权限ID
	 */
	private Integer authorityid;

	/**
	 * 子账号ID
	 */
	private Integer childid;

	/**
	 * 账号ID
	 */
	private Integer accountid;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthorityid() {
		return authorityid;
	}

	public void setAuthorityid(Integer authorityid) {
		this.authorityid = authorityid;
	}

	public Integer getChildid() {
		return childid;
	}

	public void setChildid(Integer childid) {
		this.childid = childid;
	}

	public Integer getAccountid() {
		return accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return id;
	}

}
