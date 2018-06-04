package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 账户
 * </p>
 *
 * @author wangyj
 * @since 2017-11-07
 */
@TableName("carsok_acount")
public class CarsokAcount extends Model<CarsokAcount> {

    private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 商家编号
	 */
	@TableField(value="account_code")
	private Long accountCode;

	/**
	 * 登录账号
	 */
	private String account;

	/**
	 * 登录密码
	 */
	@TableField(value="account_password")
	private String accountPassword;

	/**
	 * 账户昵称
	 */
	private String nick;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 账户类型（1:二手车商）
	 */
	@TableField(value="account_type")
	private Integer accountType;

	/**
	 * 支付密码
	 */
	@TableField(value="pay_password")
	private String payPassword;

	/**
	 * 是否绑卡（1:否、2:是）
	 */
	@TableField(value="is_bk")
	private Integer isBk;

	/**
	 * 是否实名认证（1:否、2:是）
	 */
	@TableField(value="is_real_name_audit")
	private Integer isRealNameAudit;

	/**
	 * 是否设置支付密码（1:否、2:是）
	 */
	@TableField(value="is_paypwd")
	private Integer isPaypwd;

	/**
	 * 是否商家认证（1:否、2:是）
	 */
	@TableField(value="is_merchant_audit")
	private Integer isMerchantAudit;

	/**
	 * 真实姓名
	 */
	@TableField(value="real_name")
	private String realName;

	/**
	 * 身份证号
	 */
	private String idcard;

	/**
	 * 头像访问地址
	 */
	@TableField(value="head_portrait_path")
	private String headPortraitPath;

	/**
	 * 营业执照访问地址
	 */
	@TableField(value="business_licence_path")
	private String businessLicencePath;

	/**
	 * 备用营业执照地址
	 */
	@TableField(value="business_licence_path_refuse")
	private String businessLicencePathRefuse;

	/**
	 * 秘钥
	 */
	@TableField(value="account_key")
	private String accountKey;

	/**
	 * 商户名称
	 */
	@TableField(value="merchant_name")
	private String merchantName;

	/**
	 * 商户所在省份
	 */
	private String province;

	/**
	 * 商户所在市
	 */
	private String city;

	/**
	 * 商户详细地址
	 */
	@TableField(value="merchant_address")
	private String merchantAddress;

	/**
	 * 商户介绍
	 */
	@TableField(value="merchant_descr")
	private String merchantDescr;

	/**
	 * 店招图访问地址
	 */
	@TableField(value="merchant_image_path")
	private String merchantImagePath;

	/**
	 * 创建时间
	 */
	@TableField(value="create_time")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@TableField(value="update_time")
	private Date updateTime;

	/**
	 * 车商分
	 */
	@TableField(value="quotient_score")
	private Double quotientScore;

	/**
	 * token
	 */
	private String token;

	/**
	 * 邀请人电话
	 */
	private String inviter;

	/**
	 * 
	 */
	@TableField(value="open_id")
	private String openId;

	/**
	 * 
	 */
	@TableField(value="refresh_token")
	private String refreshToken;

	/**
	 * 
	 */
	@TableField(value="wx_nick_name")
	private String wxNickName;

	/**
	 * 
	 */
	@TableField(value="wx_headUrlString")
	private String wxHeadurlstring;

	/**
	 * 
	 */
	@TableField(value="review_time")
	private Date reviewTime;

	/**
	 * '车行认证的状态(0.未认证1.审核中2.已认证)'
	 */
	@TableField(value="account_approve_status")
	private Integer accountApproveStatus;

	/**
	 * 拒绝原因
	 */
	@TableField(value="refuse_reason")
	private String refuseReason;

	/**
	 * 专业版申请进度：0：未申请 1：申请中 2：申请未通过 3：申请已通过
	 */
	@TableField(value="apply_status")
	private Integer applyStatus;

	/**
	 * 负责人姓名
	 */
	@TableField(value="contact_name")
	private String contactName;

	/**
	 * 联系电话
	 */
	@TableField(value="contact_mobile")
	private String contactMobile;

	/**
	 * 公司名称
	 */
	@TableField(value="company_name")
	private String companyName;

	/**
	 * 库存规模
	 */
	@TableField(value="merchant_size")
	private Integer merchantSize;

	/**
	 * 员工数量
	 */
	@TableField(value="employee_num")
	private Integer employeeNum;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(Long accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Integer getIsBk() {
		return isBk;
	}

	public void setIsBk(Integer isBk) {
		this.isBk = isBk;
	}

	public Integer getIsRealNameAudit() {
		return isRealNameAudit;
	}

	public void setIsRealNameAudit(Integer isRealNameAudit) {
		this.isRealNameAudit = isRealNameAudit;
	}

	public Integer getIsPaypwd() {
		return isPaypwd;
	}

	public void setIsPaypwd(Integer isPaypwd) {
		this.isPaypwd = isPaypwd;
	}

	public Integer getIsMerchantAudit() {
		return isMerchantAudit;
	}

	public void setIsMerchantAudit(Integer isMerchantAudit) {
		this.isMerchantAudit = isMerchantAudit;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getHeadPortraitPath() {
		return headPortraitPath;
	}

	public void setHeadPortraitPath(String headPortraitPath) {
		this.headPortraitPath = headPortraitPath;
	}

	public String getBusinessLicencePath() {
		return businessLicencePath;
	}

	public void setBusinessLicencePath(String businessLicencePath) {
		this.businessLicencePath = businessLicencePath;
	}

	public String getBusinessLicencePathRefuse() {
		return businessLicencePathRefuse;
	}

	public void setBusinessLicencePathRefuse(String businessLicencePathRefuse) {
		this.businessLicencePathRefuse = businessLicencePathRefuse;
	}

	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getMerchantDescr() {
		return merchantDescr;
	}

	public void setMerchantDescr(String merchantDescr) {
		this.merchantDescr = merchantDescr;
	}

	public String getMerchantImagePath() {
		return merchantImagePath;
	}

	public void setMerchantImagePath(String merchantImagePath) {
		this.merchantImagePath = merchantImagePath;
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

	public Double getQuotientScore() {
		return quotientScore;
	}

	public void setQuotientScore(Double quotientScore) {
		this.quotientScore = quotientScore;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getWxNickName() {
		return wxNickName;
	}

	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}

	public String getWxHeadurlstring() {
		return wxHeadurlstring;
	}

	public void setWxHeadurlstring(String wxHeadurlstring) {
		this.wxHeadurlstring = wxHeadurlstring;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Integer getAccountApproveStatus() {
		return accountApproveStatus;
	}

	public void setAccountApproveStatus(Integer accountApproveStatus) {
		this.accountApproveStatus = accountApproveStatus;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getMerchantSize() {
		return merchantSize;
	}

	public void setMerchantSize(Integer merchantSize) {
		this.merchantSize = merchantSize;
	}

	public Integer getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(Integer employeeNum) {
		this.employeeNum = employeeNum;
	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return id;
	}

}
