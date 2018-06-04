package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
@TableName("carsok_customer_intention_car")
public class CarsokCustomerIntentionCar extends Model<CarsokCustomerIntentionCar> {

    private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 客户id
	 */
	@TableField(value="customer_id")
	private Integer customerId;

	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 车系
	 */
	private String series;

	/**
	 * 车型
	 */
	private String model;

	//0:车系 1:意向车辆
	@TableField(value="product_status")
	private Integer productStatus;
	//0:库存 1:联盟
	@TableField(value="product_type")
	private Integer productType;
	//车辆id
	@TableField(value="product_id")
	private Integer productId;
	//车辆商家id
	@TableField(value="merchant_id")
	private Integer merchantId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return id;
	}

}
