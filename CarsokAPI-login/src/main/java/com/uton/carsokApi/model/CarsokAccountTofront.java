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
 * @author csw
 * @since 2018-01-23
 */
@TableName("carsok_account_tofront")
public class CarsokAccountTofront extends Model<CarsokAccountTofront> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 置顶车行id
     */
	@TableField("tofront_account_id")
	private Integer tofrontAccountId;

	@TableField("account_order_id")
	private Integer accountOrderId;
    /**
     * 开始日期（置顶开始日期）
     */
	@TableField("start_time")
	private Date startTime;
    /**
     * 到期日期（根据套餐计算到期日期，到期提醒，是否显示置顶根据此字段判断）
     */
	@TableField("end_time")
	private Date endTime;
    /**
     * 置顶随机排序字段
     */
	@TableField("new_order")
	private Integer newOrder;
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


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTofrontAccountId() {
		return tofrontAccountId;
	}

	public void setTofrontAccountId(Integer tofrontAccountId) {
		this.tofrontAccountId = tofrontAccountId;
	}

	public Integer getAccountOrderId() {
		return accountOrderId;
	}

	public void setAccountOrderId(Integer accountOrderId) {
		this.accountOrderId = accountOrderId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(Integer newOrder) {
		this.newOrder = newOrder;
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
		return "CarsokAccountTofront{" +
			"id=" + id +
			", tofrontAccountId=" + tofrontAccountId +
			", accountOrderId=" + accountOrderId +
			", startTime=" + startTime +
			", endTime=" + endTime +
			", newOrder=" + newOrder +
			", enable=" + enable +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
