package com.uton.carsokApi.model;

import java.util.Date;

public class Acount {
	private Integer id;

	private Long accountCode;

	private String account;

	private String accountPassword;

	private String nick;

	private String mobile;

	private Integer accountType;

	private String payPassword;

	private Short isBk;

	private Short isRealNameAudit;

	private Short isPaypwd;

	private Short isMerchantAudit;

	private String realName;

	private String idcard;

	private String headPortraitPath;

	private String businessLicencePath;

	private String accountKey;

	private String merchantName;

	private String province;

	private String city;

	private String merchantAddress;

	private String merchantDescr;

	private String merchantImagePath;

	private Date createTime;

	private Date updateTime;

	private String subPhone;

	private Double quotientScore;

	private String token;

	private String inviter;

	private String openId;

	private String refreshToken;

	private byte[] wxNickName;

	private String wxHeadUrlString;

	private Integer accountApproveStatus;

	private String refuseReason;

	private String businessLicencePathRefuse;

	private Integer merchantSize;//库存规模

	private  Integer employeeNum;//员工数量

	private Integer applyStatus;//专业版申请进度

	private String contactName;//联系人姓名

	private String contactMobile;//联系人电话

	private String companyName;//公司名称

//	private String loginAlias; //登录名
//
//	private String loginAliasPassword; //登录名对应密码
//
//	public String getLoginAlias() {
//		return loginAlias;
//	}
//
//	public void setLoginAlias(String loginAlias) {
//		this.loginAlias = loginAlias;
//	}
//
//	public String getLoginAliasPassword() {
//		return loginAliasPassword;
//	}
//
//	public void setLoginAliasPassword(String loginAliasPassword) {
//		this.loginAliasPassword = loginAliasPassword;
//	}

	public String getBusinessLicencePathRefuse() {
		return businessLicencePathRefuse;
	}

	public void setBusinessLicencePathRefuse(String businessLicencePathRefuse) {
		this.businessLicencePathRefuse = businessLicencePathRefuse;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public byte[] getWxNickName() {
		return wxNickName;
	}

	public void setWxNickName(byte[] wxNickName) {
		this.wxNickName = wxNickName;
	}

	public String getWxHeadUrlString() {
		return wxHeadUrlString;
	}

	public void setWxHeadUrlString(String wxHeadUrlString) {
		this.wxHeadUrlString = wxHeadUrlString;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Double getQuotientScore() {
		return quotientScore;
	}

	public void setQuotientScore(Double quotientScore) {
		this.quotientScore = quotientScore;
	}

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
		this.account = account == null ? null : account.trim();
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword == null ? null : accountPassword.trim();
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick == null ? null : nick.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
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
		this.payPassword = payPassword == null ? null : payPassword.trim();
	}

	public Short getIsBk() {
		return isBk;
	}

	public void setIsBk(Short isBk) {
		this.isBk = isBk;
	}

	public Short getIsRealNameAudit() {
		return isRealNameAudit;
	}

	public void setIsRealNameAudit(Short isRealNameAudit) {
		this.isRealNameAudit = isRealNameAudit;
	}

	public Short getIsPaypwd() {
		return isPaypwd;
	}

	public void setIsPaypwd(Short isPaypwd) {
		this.isPaypwd = isPaypwd;
	}

	public Short getIsMerchantAudit() {
		return isMerchantAudit;
	}

	public void setIsMerchantAudit(Short isMerchantAudit) {
		this.isMerchantAudit = isMerchantAudit;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard == null ? null : idcard.trim();
	}

	public String getHeadPortraitPath() {
		return headPortraitPath;
	}

	public void setHeadPortraitPath(String headPortraitPath) {
		this.headPortraitPath = headPortraitPath == null ? null : headPortraitPath.trim();
	}

	public String getBusinessLicencePath() {
		return businessLicencePath;
	}

	public void setBusinessLicencePath(String businessLicencePath) {
		this.businessLicencePath = businessLicencePath == null ? null : businessLicencePath.trim();
	}

	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey == null ? null : accountKey.trim();
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress == null ? null : merchantAddress.trim();
	}

	public String getMerchantDescr() {
		return merchantDescr;
	}

	public void setMerchantDescr(String merchantDescr) {
		this.merchantDescr = merchantDescr == null ? null : merchantDescr.trim();
	}

	public String getMerchantImagePath() {
		return merchantImagePath;
	}

	public void setMerchantImagePath(String merchantImagePath) {
		this.merchantImagePath = merchantImagePath == null ? null : merchantImagePath.trim();
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

	public String getSubPhone() {
		return subPhone;
	}

	public void setSubPhone(String subPhone) {
		this.subPhone = subPhone;
	}

	public Integer getAccountApproveStatus() {
		return accountApproveStatus;
	}

	public void setAccountApproveStatus(Integer accountApproveStatus) {
		this.accountApproveStatus = accountApproveStatus;
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
}