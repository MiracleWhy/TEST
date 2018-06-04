package com.uton.carsokApi.constants;

public interface UserErrorCode extends ErrorCode{
  public static final String UserLoginErrorRetCode = "1001";
  public static final String UserLoginErrorRetInfo = "用户登录失败";

  public static final String UserNotExistErrorRetCode = "1002";
  public static final String UserNotExistErrorRetInfo = "用户不存在或密码错误";
  
  public static final String UserPermissionDeniedErrorRetCode = "1003";
  public static final String UserPermissionDeniedErrorRetInfo = "用户没有权限";
  
  public static final String CaptchaErrorRetCode = "1004";
  public static final String CaptchaErrorRetInfo = "验证码错误";
  
  public static final String UserOrPassErrorRetCode = "1005";
  public static final String UserOrPassErrorRetInfo = "用户名或者密码错误";
  
  public static final String NeedStaffIDRetCode = "1006";
  public static final String NeedStaffIDRetInfo = "员工ID不能为空";
  
  public static final String NoMatchingTypeRetCode = "1007";
  public static final String NoMatchingTypeRetInfo = "修改类型不匹配";
  
  public static final String OverMaxLengthRetCode = "1008";
  public static final String OverMaxLengthRetInfo = "长度越过最大值30位";
  
  public static final String OldPwdErrorRetCode = "1009";
  public static final String OldPwdErrorRetInfo = "原密码错误";
  
  public static final String NewAndConfirmPwdDifferentRetCode = "1010";
  public static final String NewAndConfirmPwdDifferentRetInfo = "新密码和确认新密码不一致";
  
  public static final String PwdParamErrorRetCode = "1011";
  public static final String PwdParamErrorRetInfo = "密码应为6-20位，字母加数字";
  
  public static final String NeedConfirmPwdRetCode = "1012";
  public static final String NeedConfirmPwdRetInfo = "确认密码不能为空";
  
  public static final String RegisterCode = "1013";
  public static final String RegisterRetInfo = "账户已经存在";
  
  public static final String OldPwdNewPwdErrorCode = "1014";
  public static final String OldPwdNewPwdErrorfo = "原密码和新密码不能相同";
  
  public static final String OldAcountKeyCode = "1015";
  public static final String OldAcountKeyInfo = "原秘钥错误";
  
  public static final String OldAcountKeyErrorCode = "1016";
  public static final String OldAcountKeyErrorfo = "原秘钥和新秘钥不能相同";
  
  public static final String PayPwdErrorCode = "1017";
  public static final String PayPwdErrorfo = "支付密码错误";
  
  public static final String RealNameFail = "1018";
  public static final String RealNameFailInfo = "未实名认证";
  
  public static final String IsSetPayPwdFail = "1019";
  public static final String IsSetPayPwdFailInfo = "未设置支付密码";
  
  public static final String BandBankCardFail = "1020";
  public static final String BandBankCardFailInfo = "未绑定银行卡";
  
  public static final String MerchantAuditFail = "1021";
  public static final String MerchantAuditFailInfo = "未商家认证";

  public static final String ExpiredTokenErrorRetCode = "1022";
  public static final String ExpiredTokenErrorRetInfo = "token过期或失效";


  
}
