package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.dozer.Mapping;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
@TableName("carsok_customer")
public class CarsokCustomer extends Model<CarsokCustomer> {

    private static final long serialVersionUID = 1L;
	/**
	 * 自增id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 电话号码
	 */
	private String mobile;

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 生日
	 */
	private Date birthday;

	/**
	 * 来源
	 */
	private String source;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 住址
	 */
	private String address;

	/**
	 * 职业
	 */
	private String job;

	/**
	 * 性格
	 */
	private String personality;

	/**
	 * 人脉
	 */
	private String link;

	/**
	 * 购车之前车型
	 */
	@TableField(value="before_car")
	private String beforeCar;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 进店时间
	 */
	@TableField(value="in_time")
	private Date inTime;

	/**
	 * 离店时间
	 */
	@TableField(value="out_time")
	private Date outTime;

	/**
	 * 进店人数
	 */
	@TableField(value="in_number")
	private Integer inNumber;

	/**
	 * 客户级别
	 */
	private String level;

	/**
	 * 战败原因
	 */
	@TableField(value="fail_reason")
	private String failReason;

	/**
	 * 购买预算
	 */
	private String budget;

	/**
	 * 是否试驾
	 */
	@TableField(value="is_driving_test")
	private String isDrivingTest;

	/**
	 * 地域
	 */
	private String area;

	/**
	 * 创建时间
	 */
	@TableField(value="create_time")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value="update_time")
	private Date updateTime;
	@TableField(value = "account_id")
	private int accountId;
	@TableField(value = "child_id")
	private int childId;
	@TableField(value = "visit_status")
	private int visitStatus;

	public int getVisitStatus() {
		return visitStatus;
	}

	public void setVisitStatus(int visitStatus) {
		this.visitStatus = visitStatus;
	}
	@TableField(exist = false)
	private List<CarsokCustomerIntentionCar> carLists;

	@TableField(value = "province")
	private String province;
	@TableField(value = "city")
	private String city;
	@TableField(value = "district")
	private String district;
	@TableField(value = "markerCar")
	private String markerCar;

	public String getMarkerCar() {
		return markerCar;
	}

	public void setMarkerCar(String markerCar) {
		this.markerCar = markerCar;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public List<CarsokCustomerIntentionCar> getCarLists() {
		return carLists;
	}

	public void setCarLists(List<CarsokCustomerIntentionCar> carLists) {
		this.carLists = carLists;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getChildId() {
		return childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getBeforeCar() {
		return beforeCar;
	}

	public void setBeforeCar(String beforeCar) {
		this.beforeCar = beforeCar;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public Integer getInNumber() {
		return inNumber;
	}

	public void setInNumber(Integer inNumber) {
		this.inNumber = inNumber;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getIsDrivingTest() {
		return isDrivingTest;
	}

	public void setIsDrivingTest(String isDrivingTest) {
		this.isDrivingTest = isDrivingTest;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
		// TODO Auto-generated method stub
		return id;
	}

}
