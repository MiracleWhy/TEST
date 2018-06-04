package com.uton.carsokApi.model;

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
 * @author csw
 * @since 2018-01-17
 */
@TableName("carsok_quote")
public class CarsokQuote extends Model<CarsokQuote> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 意向车辆id
     */
	@TableField("intentional_product_id")
	private Integer intentionalProductId;
    /**
     * 姓名
     */
	private String name;
    /**
     * 电话
     */
	private String mobile;
    /**
     * 意向价格
     */
	@TableField("intentional_price")
	private BigDecimal intentionalPrice;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 备注
     */
	private String remark;
    /**
     * 报价商家id
     */
	@TableField("account_id")
	private Integer accountId;
    /**
     * 报价商家子账号id
     */
	@TableField("child_id")
	private Integer childId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIntentionalProductId() {
		return intentionalProductId;
	}

	public void setIntentionalProductId(Integer intentionalProductId) {
		this.intentionalProductId = intentionalProductId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getIntentionalPrice() {
		return intentionalPrice;
	}

	public void setIntentionalPrice(BigDecimal intentionalPrice) {
		this.intentionalPrice = intentionalPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CarsokQuote{" +
			"id=" + id +
			", intentionalProductId=" + intentionalProductId +
			", name=" + name +
			", mobile=" + mobile +
			", intentionalPrice=" + intentionalPrice +
			", createTime=" + createTime +
			", remark=" + remark +
			", accountId=" + accountId +
			", childId=" + childId +
			"}";
	}
}
