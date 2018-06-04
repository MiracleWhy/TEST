package com.uton.carsokApi.controller.response;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2017-12-29
 */
public class CarsokCustomerTenureServeResponse extends Model<CarsokCustomerTenureServeResponse> {

    /**
     * id
     */
	private Integer id;
    /**
     * 服务车辆ID
     */
	private Integer tenureCarId;
    /**
     * 服务类型
     */
	private String type;
    /**
     * 金额
     */
	private BigDecimal money;
    /**
     * 内容
     */
	private String info;
    /**
     * 图片地址，以逗号分隔。
     */
	private String picturesUrls;
    /**
     * 主账号id
     */
	private Integer accountId;
    /**
     * 子账号
     */
	private Integer childId;
    /**
     * 服务时间
     */
	private Date serverTime;

	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 角色
	 */
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTenureCarId() {
		return tenureCarId;
	}

	public void setTenureCarId(Integer tenureCarId) {
		this.tenureCarId = tenureCarId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPicturesUrls() {
		return picturesUrls;
	}

	public void setPicturesUrls(String picturesUrls) {
		this.picturesUrls = picturesUrls;
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

	public Date getServerTime() {
		return serverTime;
	}

	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokCustomerTenureServe{" +
			", id=" + id +
			", tenureCarId=" + tenureCarId +
			", type=" + type +
			", money=" + money +
			", info=" + info +
			", picturesUrls=" + picturesUrls +
			", accountId=" + accountId +
			", childId=" + childId +
			", serverTime=" + serverTime +
			"}";
	}
}
