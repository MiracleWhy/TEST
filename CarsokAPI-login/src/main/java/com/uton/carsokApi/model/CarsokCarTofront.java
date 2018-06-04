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
@TableName("carsok_car_tofront")
public class CarsokCarTofront extends Model<CarsokCarTofront> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 置顶车辆id
     */
	@TableField("tofront_product_id")
	private Integer tofrontProductId;
    /**
     * 车源置顶订单id(关联订单表)
     */
	@TableField("car_order_id")
	private Integer carOrderId;
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
     * 是否有效数据（0：有效  1：无效 ）
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

	public Integer getTofrontProductId() {
		return tofrontProductId;
	}

	public void setTofrontProductId(Integer tofrontProductId) {
		this.tofrontProductId = tofrontProductId;
	}

	public Integer getCarOrderId() {
		return carOrderId;
	}

	public void setCarOrderId(Integer carOrderId) {
		this.carOrderId = carOrderId;
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
		return "CarsokCarTofront{" +
			"id=" + id +
			", tofrontProductId=" + tofrontProductId +
			", carOrderId=" + carOrderId +
			", startTime=" + startTime +
			", endTime=" + endTime +
			", newOrder=" + newOrder +
			", enable=" + enable +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
