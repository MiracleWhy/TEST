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
@TableName("carsok_account_collect")
public class CarsokAccountCollect extends Model<CarsokAccountCollect> {

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
     * 收藏车行id
     */
	@TableField("collect_account_id")
	private Integer collectAccountId;
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

	public Integer getCollectAccountId() {
		return collectAccountId;
	}

	public void setCollectAccountId(Integer collectAccountId) {
		this.collectAccountId = collectAccountId;
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
		return "CarsokAccountCollect{" +
			"id=" + id +
			", accountId=" + accountId +
			", childId=" + childId +
			", collectAccountId=" + collectAccountId +
			", enable=" + enable +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
