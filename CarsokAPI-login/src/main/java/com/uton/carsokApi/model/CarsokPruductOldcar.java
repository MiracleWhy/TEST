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
 * 商品辅表（二手车）
 * </p>
 *
 * @author csw
 * @since 2017-11-10
 */
@TableName("carsok_pruduct_oldcar")
public class CarsokPruductOldcar extends Model<CarsokPruductOldcar> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品ID
     */
	@TableField("pruduct_id")
	private Integer pruductId;
    /**
     * 品牌
     */
	@TableField("c_brand")
	private String cBrand;
    /**
     * 车系
     */
	@TableField("c_type")
	private String cType;
    /**
     * 车型
     */
	@TableField("c_model")
	private String cModel;
    /**
     * 所在省份
     */
	private String province;
    /**
     * 所在市
     */
	private String city;
    /**
     * 车辆里程（单位：公里）
     */
	private Integer miles;
    /**
     * 首次上牌日期
     */
	@TableField("first_up_time")
	private Date firstUpTime;
    /**
     * 联系人
     */
	private String linkman;
    /**
     * 联系电话
     */
	private String linkphone;
    /**
     * 浏览次数
     */
	@TableField("browse_num_times")
	private Integer browseNumTimes;
    /**
     * 电话联系次数
     */
	@TableField("tel_num_times")
	private Integer telNumTimes;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;
	@TableField("browse_num_times_fake")
	private Integer browseNumTimesFake;
    /**
     * 变数箱
     */
	@TableField("ariable_box")
	private String ariableBox;
    /**
     * 颜色
     */
	@TableField("car_colour")
	private String carColour;
    /**
     * 排量
     */
	private String displacement;
    /**
     * 室内装置
     */
	@TableField("indoor_type")
	private String indoorType;
    /**
     * 车身结构
     */
	@TableField("body_structure")
	private String bodyStructure;
    /**
     * 发动机
     */
	private String engine;
    /**
     * 驱动轮
     */
	@TableField("driving_wheel")
	private String drivingWheel;
    /**
     * 发票价
     */
	private String invoice;
	/**
	 * 今日浏览次数
	 */
	@TableField("today_browse_num_times")
	private  int todayBrowseNumTimes;

	public int getTodayBrowseNumTimes() {
		return todayBrowseNumTimes;
	}

	public void setTodayBrowseNumTimes(int todayBrowseNumTimes) {
		this.todayBrowseNumTimes = todayBrowseNumTimes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPruductId() {
		return pruductId;
	}

	public void setPruductId(Integer pruductId) {
		this.pruductId = pruductId;
	}

	public String getcBrand() {
		return cBrand;
	}

	public void setcBrand(String cBrand) {
		this.cBrand = cBrand;
	}

	public String getcType() {
		return cType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public String getcModel() {
		return cModel;
	}

	public void setcModel(String cModel) {
		this.cModel = cModel;
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

	public Integer getMiles() {
		return miles;
	}

	public void setMiles(Integer miles) {
		this.miles = miles;
	}

	public Date getFirstUpTime() {
		return firstUpTime;
	}

	public void setFirstUpTime(Date firstUpTime) {
		this.firstUpTime = firstUpTime;
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

	public Integer getBrowseNumTimes() {
		return browseNumTimes;
	}

	public void setBrowseNumTimes(Integer browseNumTimes) {
		this.browseNumTimes = browseNumTimes;
	}

	public Integer getTelNumTimes() {
		return telNumTimes;
	}

	public void setTelNumTimes(Integer telNumTimes) {
		this.telNumTimes = telNumTimes;
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

	public Integer getBrowseNumTimesFake() {
		return browseNumTimesFake;
	}

	public void setBrowseNumTimesFake(Integer browseNumTimesFake) {
		this.browseNumTimesFake = browseNumTimesFake;
	}

	public String getAriableBox() {
		return ariableBox;
	}

	public void setAriableBox(String ariableBox) {
		this.ariableBox = ariableBox;
	}

	public String getCarColour() {
		return carColour;
	}

	public void setCarColour(String carColour) {
		this.carColour = carColour;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	public String getIndoorType() {
		return indoorType;
	}

	public void setIndoorType(String indoorType) {
		this.indoorType = indoorType;
	}

	public String getBodyStructure() {
		return bodyStructure;
	}

	public void setBodyStructure(String bodyStructure) {
		this.bodyStructure = bodyStructure;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getDrivingWheel() {
		return drivingWheel;
	}

	public void setDrivingWheel(String drivingWheel) {
		this.drivingWheel = drivingWheel;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokPruductOldcar{" +
			"id=" + id +
			", pruductId=" + pruductId +
			", cBrand=" + cBrand +
			", cType=" + cType +
			", cModel=" + cModel +
			", province=" + province +
			", city=" + city +
			", miles=" + miles +
			", firstUpTime=" + firstUpTime +
			", linkman=" + linkman +
			", linkphone=" + linkphone +
			", browseNumTimes=" + browseNumTimes +
			", telNumTimes=" + telNumTimes +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", browseNumTimesFake=" + browseNumTimesFake +
			", ariableBox=" + ariableBox +
			", carColour=" + carColour +
			", displacement=" + displacement +
			", indoorType=" + indoorType +
			", bodyStructure=" + bodyStructure +
			", engine=" + engine +
			", drivingWheel=" + drivingWheel +
			", invoice=" + invoice +
			"}";
	}
}
