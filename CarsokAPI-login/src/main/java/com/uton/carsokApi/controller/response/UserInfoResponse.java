package com.uton.carsokApi.controller.response;

import java.math.BigDecimal;

/**
 * 用户详细信息res
 * 
 * @author bing.cheng
 *
 */
public class UserInfoResponse {
	private Integer id;
    private Long accountCode;

    private String account;

    private String nick;

    private String mobile;

    private Short isBk;

    private Short isRealNameAudit;

    private Short isPaypwd;

    private Short isMerchantAudit;

    private String realName;

    private String headPortraitPath;

    private String merchantName;

    private String merchantAddress;

    private String merchantDescr;

    private String merchantImagePath;
    
	private BigDecimal avail;
	
	private BigDecimal frozen;
	
	private String idcard;
	
    private String province;

    private String city;
    
    private String businessLicencePath;
    
    private String account_key;
    
    private String share_url;
    
    private String refreshToken;
    
    private String wxNickName;
    
    private String wxHeadUrlString;
    
    private String openId;

	private Integer accountApproveStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWxNickName() {
		return wxNickName;
	}

	public void setWxNickName(String wxNickName) {
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
		this.realName = realName;
	}

	public String getHeadPortraitPath() {
		return headPortraitPath;
	}

	public void setHeadPortraitPath(String headPortraitPath) {
		this.headPortraitPath = headPortraitPath;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public BigDecimal getAvail() {
		return avail;
	}

	public void setAvail(BigDecimal avail) {
		this.avail = avail;
	}

	public BigDecimal getFrozen() {
		return frozen;
	}

	public void setFrozen(BigDecimal frozen) {
		this.frozen = frozen;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
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

	public String getBusinessLicencePath() {
		return businessLicencePath;
	}

	public void setBusinessLicencePath(String businessLicencePath) {
		this.businessLicencePath = businessLicencePath;
	}

	public String getAccount_key() {
		return account_key;
	}

	public void setAccount_key(String account_key) {
		this.account_key = account_key;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public Integer getAccountApproveStatus() {
		return accountApproveStatus;
	}

	public void setAccountApproveStatus(Integer accountApproveStatus) {
		this.accountApproveStatus = accountApproveStatus;
	}
}
