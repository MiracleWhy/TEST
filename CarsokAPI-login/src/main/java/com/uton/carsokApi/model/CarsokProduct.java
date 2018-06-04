package com.uton.carsokApi.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author csw
 * @since 2017-11-09
 */
@TableName("carsok_product")
public class CarsokProduct extends Model<CarsokProduct> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品编号
     */
	@TableField("product_no")
	private String productNo;
    /**
     * 名称
     */
	@TableField("product_name")
	private String productName;
    /**
     * 描述
     */
	@TableField("product_descr")
	private String productDescr;
    /**
     * 搜索关键词
     */
	@TableField("search_key_words")
	private String searchKeyWords;
    /**
     * 商品简称
     */
	@TableField("product_short_name")
	private String productShortName;
    /**
     * 销售价(单位:元，精确到分)
     */
	private BigDecimal price;
    /**
     * 保底价(单位:元，精确到分)
     */
	private BigDecimal miniprice;
    /**
     * 商品分类 0 商家 1 个人
     */
	private Integer category;
    /**
     * 关联个人用户
     */
	private String openid;
    /**
     * 账户ID
     */
	@TableField("account_id")
	private Integer accountId;
    /**
     * 主图ID (废弃)
     */
	@TableField("primary_pic_id")
	private Integer primaryPicId;
    /**
     * 上架状态 0 下架 1 上架 2 审核中 3审核未通过 4新建待提交审核
     */
	@TableField("on_shelf_status")
	private Integer onShelfStatus;
    /**
     * 销售状态 0 在售 1售出
     */
	@TableField("sale_status")
	private Integer saleStatus;
    /**
     * 是否删除 0 未删除 1 删除
     */
	@TableField("is_del")
	private Integer isDel;
    /**
     * 上架时间
     */
	@TableField("on_shelf_time")
	private Date onShelfTime;
    /**
     * 下架时间
     */
	@TableField("off_shelf_time")
	private Date offShelfTime;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;
    /**
     * 销售人
     */
	@TableField("saled_people")
	private String saledPeople;
    /**
     * 售出价格
     */
	@TableField("saled_price")
	private BigDecimal saledPrice;
    /**
     * 售出方式（1：全款  2：贷款）
     */
	@TableField("saled_type")
	private Integer saledType;
	private String vin;
	@TableField("maintain_info")
	private String maintainInfo;
	@TableField("business_if")
	private Integer businessIf;
    /**
     * 车辆是否已定0，1
     */
	@TableField("reserve_if")
	private Integer reserveIf;
    /**
     * 收车价格
     */
	private BigDecimal closeingPrice;
    /**
     * 是否为寄售 1：是 2：否
     */
	private Integer consignment;
    /**
     * 配置
     */
	private String configuration;
    /**
     * 座位数
     */
	@TableField("seat_number")
	private Integer seatNumber;
    /**
     * 排放标准
     */
	private String exhaust;
    /**
     * 生产日期
     */
	@TableField("production_date")
	private Date productionDate;
    /**
     * 入库日期
     */
	@TableField("instore_date")
	private Date instoreDate;
    /**
     * 购车类型
     */
	@TableField("buy_type")
	private String buyType;
    /**
     * 变速箱
     */
	@TableField("ariable_box")
	private String ariableBox;
    /**
     * 车况描述
     */
	private String condition;
	private String displacement;
    /**
     * 车型库id
     */
	@TableField("car_id")
	private Integer carId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescr() {
		return productDescr;
	}

	public void setProductDescr(String productDescr) {
		this.productDescr = productDescr;
	}

	public String getSearchKeyWords() {
		return searchKeyWords;
	}

	public void setSearchKeyWords(String searchKeyWords) {
		this.searchKeyWords = searchKeyWords;
	}

	public String getProductShortName() {
		return productShortName;
	}

	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMiniprice() {
		return miniprice;
	}

	public void setMiniprice(BigDecimal miniprice) {
		this.miniprice = miniprice;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getPrimaryPicId() {
		return primaryPicId;
	}

	public void setPrimaryPicId(Integer primaryPicId) {
		this.primaryPicId = primaryPicId;
	}

	public Integer getOnShelfStatus() {
		return onShelfStatus;
	}

	public void setOnShelfStatus(Integer onShelfStatus) {
		this.onShelfStatus = onShelfStatus;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getOnShelfTime() {
		return onShelfTime;
	}

	public void setOnShelfTime(Date onShelfTime) {
		this.onShelfTime = onShelfTime;
	}

	public Date getOffShelfTime() {
		return offShelfTime;
	}

	public void setOffShelfTime(Date offShelfTime) {
		this.offShelfTime = offShelfTime;
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

	public String getSaledPeople() {
		return saledPeople;
	}

	public void setSaledPeople(String saledPeople) {
		this.saledPeople = saledPeople;
	}

	public BigDecimal getSaledPrice() {
		return saledPrice;
	}

	public void setSaledPrice(BigDecimal saledPrice) {
		this.saledPrice = saledPrice;
	}

	public Integer getSaledType() {
		return saledType;
	}

	public void setSaledType(Integer saledType) {
		this.saledType = saledType;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMaintainInfo() {
		return maintainInfo;
	}

	public void setMaintainInfo(String maintainInfo) {
		this.maintainInfo = maintainInfo;
	}

	public Integer getBusinessIf() {
		return businessIf;
	}

	public void setBusinessIf(Integer businessIf) {
		this.businessIf = businessIf;
	}

	public Integer getReserveIf() {
		return reserveIf;
	}

	public void setReserveIf(Integer reserveIf) {
		this.reserveIf = reserveIf;
	}

	public BigDecimal getCloseingPrice() {
		return closeingPrice;
	}

	public void setCloseingPrice(BigDecimal closeingPrice) {
		this.closeingPrice = closeingPrice;
	}

	public Integer getConsignment() {
		return consignment;
	}

	public void setConsignment(Integer consignment) {
		this.consignment = consignment;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getExhaust() {
		return exhaust;
	}

	public void setExhaust(String exhaust) {
		this.exhaust = exhaust;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Date getInstoreDate() {
		return instoreDate;
	}

	public void setInstoreDate(Date instoreDate) {
		this.instoreDate = instoreDate;
	}

	public String getBuyType() {
		return buyType;
	}

	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}

	public String getAriableBox() {
		return ariableBox;
	}

	public void setAriableBox(String ariableBox) {
		this.ariableBox = ariableBox;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokProduct{" +
			"id=" + id +
			", productNo=" + productNo +
			", productName=" + productName +
			", productDescr=" + productDescr +
			", searchKeyWords=" + searchKeyWords +
			", productShortName=" + productShortName +
			", price=" + price +
			", miniprice=" + miniprice +
			", category=" + category +
			", openid=" + openid +
			", accountId=" + accountId +
			", primaryPicId=" + primaryPicId +
			", onShelfStatus=" + onShelfStatus +
			", saleStatus=" + saleStatus +
			", isDel=" + isDel +
			", onShelfTime=" + onShelfTime +
			", offShelfTime=" + offShelfTime +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", saledPeople=" + saledPeople +
			", saledPrice=" + saledPrice +
			", saledType=" + saledType +
			", vin=" + vin +
			", maintainInfo=" + maintainInfo +
			", businessIf=" + businessIf +
			", reserveIf=" + reserveIf +
			", closeingPrice=" + closeingPrice +
			", consignment=" + consignment +
			", configuration=" + configuration +
			", seatNumber=" + seatNumber +
			", exhaust=" + exhaust +
			", productionDate=" + productionDate +
			", instoreDate=" + instoreDate +
			", buyType=" + buyType +
			", ariableBox=" + ariableBox +
			", condition=" + condition +
			", displacement=" + displacement +
			", carId=" + carId +
			"}";
	}
}
