package com.uton.carsokApi.constants;

public interface LoginErrorCode extends ErrorCode{
	
  public static final String LOGIN_ACCOUNT_INFO_CODE = "2001";
  public static final String LOGIN_ACCOUNT_INFO_MESSAGE = "登录提交信息为空";
	
  public static final String StaffNotExistErrorRetCode = "2002";
  public static final String StaffNotExistErrorRetInfo = "您没有权限";
  
  public static final String PhoneNumExistRetCode ="2003";
  public static final String PhoneNumExistRetInfo ="手机号已经存在";
  
  public static final String GET_VALIDATION_CODE ="2004";
  public static final String GET_VALIDATION_CODE_MESSAGE ="获取验证码,手机号为空";
  
  public static final String NOT_LOGIN_CODE = "2005";
  public static final String NOT_LOGIN_MESSAGE = "请登录";

}
