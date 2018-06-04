package com.uton.carsokApi.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @author 
 * @since 2017-11-14
 */
@TableName("carsok_account_power_manage")
public class CarsokAccountPowerManage extends Model<CarsokAccountPowerManage> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 权限名称
     */
	@TableField("power_name")
	private String powerName;
    /**
     * 权限名称值
     */
	@TableField("power_value")
	private String powerValue;
    /**
     * 关联主权限id
     */
	@TableField("parent_id")
	private Integer parentId;
    /**
     * 权限级别
     */
	@TableField("power_status")
	private Integer powerStatus;
    /**
     * icon图片
     */
	@TableField("icon_name")
	private String iconName;
    /**
     * 专业版标识
     */
	private String type;
	@TableField("show_professionallcon")
	private Integer showProfessionallcon;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

	public String getPowerValue() {
		return powerValue;
	}

	public void setPowerValue(String powerValue) {
		this.powerValue = powerValue;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(Integer powerStatus) {
		this.powerStatus = powerStatus;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getShowProfessionallcon() {
		return showProfessionallcon;
	}

	public void setShowProfessionallcon(Integer showProfessionallcon) {
		this.showProfessionallcon = showProfessionallcon;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokAccountPowerManage{" +
			", id=" + id +
			", powerName=" + powerName +
			", powerValue=" + powerValue +
			", parentId=" + parentId +
			", powerStatus=" + powerStatus +
			", iconName=" + iconName +
			", type=" + type +
			", showProfessionallcon=" + showProfessionallcon +
			"}";
	}
}
