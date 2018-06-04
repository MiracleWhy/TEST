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
 * @since 2017-11-09
 */
@TableName("carsok_car_stock")
public class CarsokCarStock extends Model<CarsokCarStock> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 品牌
     */
	@TableField("car_brand")
	private String carBrand;
    /**
     * 品牌id
     */
	@TableField("car_brand_id")
	private Integer carBrandId;
    /**
     * 车型
     */
	@TableField("car_series")
	private String carSeries;
    /**
     * 所属人
     */
	@TableField("account_id")
	private String accountId;
	@TableField("child_id")
	private Integer childId;
    /**
     * 是否可用 0:不可用 1：可用
     */
	private Integer enable;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;

    @TableField(exist = false)
    private String picPath;

    @TableField(exist = false)
    private String like;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public Integer getCarBrandId() {
		return carBrandId;
	}

	public void setCarBrandId(Integer carBrandId) {
		this.carBrandId = carBrandId;
	}

	public String getCarSeries() {
		return carSeries;
	}

	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
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

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    @Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokCarStock{" +
			"id=" + id +
			", carBrand=" + carBrand +
			", carBrandId=" + carBrandId +
			", carSeries=" + carSeries +
			", accountId=" + accountId +
			", childId=" + childId +
			", enable=" + enable +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", picPath=" + picPath +
			", like=" + like +
			"}";
	}
}
