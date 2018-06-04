package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author csw
 * @since 2018-01-18
 */
@TableName("carsok_tofront_plan")
public class CarsokTofrontPlan extends Model<CarsokTofrontPlan> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 套餐名称
     */
	@TableField("plan_name")
	private String planName;
    /**
     * 套餐价格
     */
	@TableField("plan_price")
	private BigDecimal planPrice;
    /**
     * 订单时效（天）
     */
	@TableField("plan_time")
	private Integer planTime;
    /**
     * 套餐类型（1：车行置顶套餐 2：车辆置顶套餐）
     */
	@TableField("plan_type")
	private Integer planType;
    /**
     * 备注
     */
	private String remark;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public BigDecimal getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(BigDecimal planPrice) {
		this.planPrice = planPrice;
	}

	public Integer getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Integer planTime) {
		this.planTime = planTime;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokTofrontPlan{" +
			"id=" + id +
			", planName=" + planName +
			", planPrice=" + planPrice +
			", planTime=" + planTime +
			", planType=" + planType +
			", remark=" + remark +
			"}";
	}
}
