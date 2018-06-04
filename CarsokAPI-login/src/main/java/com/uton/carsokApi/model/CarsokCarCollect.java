package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@TableName("carsok_car_collect")
public class CarsokCarCollect extends Model<CarsokCarCollect> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 车行id
     */
	@TableField("account_id")
	private Integer accountId;
    /**
     * 子账号id
     */
	@TableField("child_id")
	private Integer childId;
    /**
     * 收藏车辆id
     */
	@TableField("collect_product_id")
	private Integer collectProductId;
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

	public Integer getCollectProductId() {
		return collectProductId;
	}

	public void setCollectProductId(Integer collectProductId) {
		this.collectProductId = collectProductId;
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
		return "CarsokCarCollect{" +
			"id=" + id +
			", accountId=" + accountId +
			", childId=" + childId +
			", collectProductId=" + collectProductId +
			", enable=" + enable +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
