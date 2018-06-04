package com.uton.carsokApi.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * @author csw
 * @since 2018-01-23
 */
@TableName("carsok_account_tofront_order")
public class CarsokAccountTofrontOrder extends Model<CarsokAccountTofrontOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 置顶套餐表id
     */
	@TableField("tofront_plan_id")
	private Integer tofrontPlanId;
    /**
     * 订单编号
     */
	@TableField("order_no")
	private String orderNo;
    /**
     * 订单类型
     */
	@TableField("order_type")
	private String orderType;
    /**
     * 订单金额
     */
	@TableField("order_price")
	private BigDecimal orderPrice;
    /**
     * 是否成功（0：交易成功 1：交易失败）
     */
	@TableField("is_success")
	private Integer isSuccess;
    /**
     * 是否有效数据（0：有效  1：无效）
     */
	private Integer enable;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;

	//支付时间
	@TableField("pay_time")
	private Date payTime;

	//置顶车行id
	@TableField("tofront_account_id")
	private Integer tofrontAccountId;

	/**
	 * 操作主账号id
	 */
	@TableField("account_id")
	private Integer accountId;
	/**
	 * 操作子账号id
	 */
	@TableField("child_id")
	private Integer childId;

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

	/**
	 * 车行置顶订单id
	 */



	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getTofrontAccountId() {
		return tofrontAccountId;
	}

	public void setTofrontAccountId(Integer tofrontAccountId) {
		this.tofrontAccountId = tofrontAccountId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTofrontPlanId() {
		return tofrontPlanId;
	}

	public void setTofrontPlanId(Integer tofrontPlanId) {
		this.tofrontPlanId = tofrontPlanId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokAccountTofrontOrder{" +
			"id=" + id +
			", tofrontPlanId=" + tofrontPlanId +
			", orderNo=" + orderNo +
			", orderType=" + orderType +
			", orderPrice=" + orderPrice +
			", isSuccess=" + isSuccess +
			", enable=" + enable +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
