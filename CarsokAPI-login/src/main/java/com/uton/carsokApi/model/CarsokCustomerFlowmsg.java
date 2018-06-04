package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
@TableName("carsok_customer_flowmsg")
public class CarsokCustomerFlowmsg extends Model<CarsokCustomerFlowmsg> {

    private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 客户ID
	 */
	@TableField(value="customer_id")
	private Integer customerId;

	/**
	 * 客户跟进
	 */
	@TableField(value="customer_flow_message")
	private String customerFlowMessage;

	/**
	 * 创建时间
	 */
	@TableField(value="create_time")
	private Date createTime;

	/**
	 * 到访状态
	 */
	private Integer status;

	/**
	 * 推送时间
	 */
	@TableField(value="push_time")
	private Date pushTime;

	/**
	 * 电话邀约 1：到店  2：未到店   -1:老版本再次
	 */
	@TableField(value="cust_come")
	private Integer custCome;

	/**
	 * 主帐号id（跟进人）
	 */
	@TableField(value="account_id")
	private Integer accountId;

	/**
	 * 子帐号id（跟进人）
	 */
	@TableField(value="child_id")
	private Integer childId;

	/**
	 * 客服跟进json
	 */
	@TableField(value="cus_service_follow")
	private String cusServiceFollow;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFlowMessage() {
		return customerFlowMessage;
	}

	public void setCustomerFlowMessage(String customerFlowMessage) {
		this.customerFlowMessage = customerFlowMessage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getCustCome() {
		return custCome;
	}

	public void setCustCome(Integer custCome) {
		this.custCome = custCome;
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

	public String getCusServiceFollow() {
		return cusServiceFollow;
	}

	public void setCusServiceFollow(String cusServiceFollow) {
		this.cusServiceFollow = cusServiceFollow;
	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return id;
	}

}
