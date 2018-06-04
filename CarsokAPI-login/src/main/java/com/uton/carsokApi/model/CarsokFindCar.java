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
@TableName("carsok_find_car")
public class CarsokFindCar extends Model<CarsokFindCar> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 意向车辆编号
     */
	@TableField("intentional_no")
	private String intentionalNo;
    /**
     * 发布车行id
     */
	@TableField("account_id")
	private Integer accountId;
    /**
     * 发布车行子账号id
     */
	@TableField("child_id")
	private Integer childId;
    /**
     * 品牌
     */
	private String brand;

	/**
	 * 品牌Id
	 */
	@TableField("brand_id")
	private Integer brandId;
    /**
     * 车型
     */
	private String model;
    /**
     * 配置
     */
	private String configuration;
    /**
     * 颜色
     */
	@TableField("car_colour")
	private String carColour;
    /**
     * 联系人
     */
	private String linkman;
    /**
     * 联系电话
     */
	private String linkphone;
    /**
     * 上牌省
     */
	private String province;
    /**
     * 上牌市
     */
	private String city;
	@TableField("first_up_time")
	private Date firstUpTime;
    /**
     * 备注
     */
	private String remark;
    /**
     * 是否有效数据（0：有效 1：无效）
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

	/**
	 * 内饰
	 */
	@TableField("indoor_type")
	private String indoorType;


	public String getIndoorType() {
		return indoorType;
	}

	public void setIndoorType(String indoorType) {
		this.indoorType = indoorType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIntentionalNo() {
		return intentionalNo;
	}

	public void setIntentionalNo(String intentionalNo) {
		this.intentionalNo = intentionalNo;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getCarColour() {
		return carColour;
	}

	public void setCarColour(String carColour) {
		this.carColour = carColour;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getFirstUpTime() {
		return firstUpTime;
	}

	public void setFirstUpTime(Date firstUpTime) {
		this.firstUpTime = firstUpTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokFindCar{" +
				"id=" + id +
				", intentionalNo='" + intentionalNo + '\'' +
				", accountId=" + accountId +
				", childId=" + childId +
				", brand='" + brand + '\'' +
				", brandId=" + brandId +
				", model='" + model + '\'' +
				", configuration='" + configuration + '\'' +
				", carColour='" + carColour + '\'' +
				", linkman='" + linkman + '\'' +
				", linkphone='" + linkphone + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", firstUpTime=" + firstUpTime +
				", remark='" + remark + '\'' +
				", enable=" + enable +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
