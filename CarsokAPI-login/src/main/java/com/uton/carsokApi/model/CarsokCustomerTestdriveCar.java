package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhanghaopeng
 * @since 2017-12-18
 */
@TableName("carsok_customer_testdrive_car")
public class CarsokCustomerTestdriveCar extends Model<CarsokCustomerTestdriveCar> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 客户id
     */
	@TableField("customer_id")
	private Integer customerId;
    /**
     * 品牌
     */
	private String brand;
    /**
     * 车系
     */
	private String series;
	private String model;


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
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokCustomerTestdriveCar{" +
			"id=" + id +
			", customerId=" + customerId +
			", brand=" + brand +
			", series=" + series +
			", model=" + model +
			"}";
	}
}
