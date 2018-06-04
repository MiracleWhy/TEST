package com.uton.carsokApi.model;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
@TableName("carsok_customer_tenure_car")
public class CarsokCustomerTenureCar extends Model<CarsokCustomerTenureCar> {

    private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 车辆名称
	 */
	@TableField(value="tenure_carname")
	private String tenureCarname;

	/**
	 * 车辆vin码
	 */
	@TableField(value="tenure_vin")
	private String tenureVin;

	/**
	 * 车牌号
	 */
	@TableField(value="tenure_carnum")
	private String tenureCarnum;

	/**
	 * 出售方式
	 */
	@TableField(value="tenure_cartype")
	private String tenureCartype;

	/**
	 * 交强险到期日
	 */
	@TableField(value="tenure_compulsory")
	private Date tenureCompulsory;

	/**
	 * 商业险到期日
	 */
	@TableField(value="tenure_business")
	private Date tenureBusiness;

	/**
	 * 保养到期日
	 */
	@TableField(value="tenure_maintain")
	private Date tenureMaintain;

	/**
	 * 质保到期日
	 */
	@TableField(value="tenure_Warranty")
	private Date tenureWarranty;

	/**
	 * 价格
	 */
	@TableField(value="tenure_carprice")
	private BigDecimal tenureCarprice;

	/**
	 * product  ID
	 */
	@TableField(value="product_id")
	private Integer productId;

	/**
	 * 
	 */
	@TableField(value="create_time")
	private Date createTime;

	/**
	 * 
	 */
	@TableField(value="update_time")
	private Date updateTime;
	/**
	 * 标识 1可用 0不可用
	 */
	private Integer enable;

	/**
	 * 销售人
	 */
	@TableField(value="sale_people")
	private String salePeople;

	/**
	 * 行驶里程
	 */
	@TableField(value="car_miles")
	private String carMiles;

	/**
	 * 出售时间
	 */
	@TableField(value="sale_time")
	private Date saleTime;

	/**
	 * accountId
	 */
	@TableField(value="account_id")
	private Integer accountId;

	/**
	 * childId
	 */
	@TableField(value="child_id")
	private Integer childId;

	/**
	 * 
	 */
	private String brand;

	/**
	 * 
	 */
	@TableField(value="three_status")
	private Integer threeStatus;

	/**
	 * 
	 */
	@TableField(value="seven_status")
	private Integer sevenStatus;

	/**
	 * 所属客户id
	 */
	@TableField(value="customer_id")
	private Integer customerId;

	/**
	 * 购买状态
	 */
	@TableField(value="purchase_status")
	private String purchaseStatus;
	

	/**
	 * 是否试驾
	 */
	@TableField(value="is_driving_test")
	private String isDrivingTest;

	/**
	 * 是否投保商业险
	 */
	@TableField(value="is_bussiness")
	private String isBussiness;

	/**
	 * 是否是新增保有客户(1-是)
	 */
	@TableField(value="is_new_record")
	private Integer isNewRecord;

	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 是否完善(1-是)
	 */
	@TableField(value="is_done")
	private Integer isDone;

//	/**
//	 * 年检到期日
//	 */
//	@TableField("tenure_inspection")
//	private Date tenureInspection;

//	/**
//	 * 救援日期
//	 */
//	@TableField("tenure_rescue")
//	private Date tenureRescue;
//
//	/**
//	 * 洗车日期
//	 */
//	@TableField("tenure_carwash")
//	private Date tenureCarwash;
//
//	/**
//	 * 钣金喷漆日期
//	 */
//	@TableField("tenure_paint")
//	private Date tenurePaint;

//	public Date getTenureRescue() {
//		return tenureRescue;
//	}
//
//	public void setTenureRescue(Date tenureRescue) {
//		this.tenureRescue = tenureRescue;
//	}
//
//	public Date getTenureCarwash() {
//		return tenureCarwash;
//	}
//
//	public void setTenureCarwash(Date tenureCarwash) {
//		this.tenureCarwash = tenureCarwash;
//	}
//
//	public Date getTenurePaint() {
//		return tenurePaint;
//	}
//
//	public void setTenurePaint(Date tenurePaint) {
//		this.tenurePaint = tenurePaint;
//	}

//	public Date getTenureInspection() {
//		return tenureInspection;
//	}
//
//	public void setTenureInspection(Date tenureInspection) {
//		this.tenureInspection = tenureInspection;
//	}

	public Integer getIsDone() {
		return isDone;
	}

	public void setIsDone(Integer isDone) {
		this.isDone = isDone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTenureCarname() {
		return tenureCarname;
	}

	public void setTenureCarname(String tenureCarname) {
		this.tenureCarname = tenureCarname;
	}

	public String getTenureVin() {
		return tenureVin;
	}

	public void setTenureVin(String tenureVin) {
		this.tenureVin = tenureVin;
	}

	public String getTenureCarnum() {
		return tenureCarnum;
	}

	public void setTenureCarnum(String tenureCarnum) {
		this.tenureCarnum = tenureCarnum;
	}

	public String getTenureCartype() {
		return tenureCartype;
	}

	public void setTenureCartype(String tenureCartype) {
		this.tenureCartype = tenureCartype;
	}

	public Date getTenureCompulsory() {
		return tenureCompulsory;
	}

	public void setTenureCompulsory(Date tenureCompulsory) {
		this.tenureCompulsory = tenureCompulsory;
	}

	public Date getTenureBusiness() {
		return tenureBusiness;
	}

	public void setTenureBusiness(Date tenureBusiness) {
		this.tenureBusiness = tenureBusiness;
	}

	public Date getTenureMaintain() {
		return tenureMaintain;
	}

	public void setTenureMaintain(Date tenureMaintain) {
		this.tenureMaintain = tenureMaintain;
	}

	public Date getTenureWarranty() {
		return tenureWarranty;
	}

	public void setTenureWarranty(Date tenureWarranty) {
		this.tenureWarranty = tenureWarranty;
	}

	public BigDecimal getTenureCarprice() {
		return tenureCarprice;
	}

	public void setTenureCarprice(BigDecimal tenureCarprice) {
		this.tenureCarprice = tenureCarprice;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getSalePeople() {
		return salePeople;
	}

	public void setSalePeople(String salePeople) {
		this.salePeople = salePeople;
	}

	public String getCarMiles() {
		return carMiles;
	}

	public void setCarMiles(String carMiles) {
		this.carMiles = carMiles;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
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

	public Integer getThreeStatus() {
		return threeStatus;
	}

	public void setThreeStatus(Integer threeStatus) {
		this.threeStatus = threeStatus;
	}

	public Integer getSevenStatus() {
		return sevenStatus;
	}

	public void setSevenStatus(Integer sevenStatus) {
		this.sevenStatus = sevenStatus;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	public String getIsDrivingTest() {
		return isDrivingTest;
	}

	public void setIsDrivingTest(String isDrivingTest) {
		this.isDrivingTest = isDrivingTest;
	}

	public String getIsBussiness() {
		return isBussiness;
	}

	public void setIsBussiness(String isBussiness) {
		this.isBussiness = isBussiness;
	}

	public Integer getIsNewRecord() {
		return isNewRecord;
	}

	public void setIsNewRecord(Integer isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return id;
	}

}
