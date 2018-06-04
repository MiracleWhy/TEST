package com.uton.carsokApi.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangzheng
 * @since 2017-11-11
 */
@TableName("carsok_customer_tenure_follow")
public class CarsokCustomerTenureFollow extends Model<CarsokCustomerTenureFollow> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("tenurecar_id")
	private Integer tenurecarId;
	@TableField("follow_message")
	private String followMessage;
    /**
     * 1:初次购买 2:3日电话回访 3:7日电话回访
     */
	@TableField("follow_type")
	private String followType;
	@TableField("account_id")
	private Integer accountId;
	@TableField("child_id")
	private Integer childId;
	@TableField("create_time")
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTenurecarId() {
		return tenurecarId;
	}

	public void setTenurecarId(Integer tenurecarId) {
		this.tenurecarId = tenurecarId;
	}

	public String getFollowMessage() {
		return followMessage;
	}

	public void setFollowMessage(String followMessage) {
		this.followMessage = followMessage;
	}

	public String getFollowType() {
		return followType;
	}

	public void setFollowType(String followType) {
		this.followType = followType;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokCustomerTenureFollow{" +
			", id=" + id +
			", tenurecarId=" + tenurecarId +
			", followMessage=" + followMessage +
			", followType=" + followType +
			", accountId=" + accountId +
			", childId=" + childId +
			", createTime=" + createTime +
			"}";
	}
}
