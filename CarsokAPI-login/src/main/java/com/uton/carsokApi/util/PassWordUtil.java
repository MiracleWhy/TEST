package com.uton.carsokApi.util;
/**
 * 密码设置工作类
 * @author bing.cheng
 *
 */
public class PassWordUtil {

	/**
	 * 支付密码
	 * @param acount
	 * @param password
	 * @return
	 */
	public static String getMd5PayPassWord(String acount, String password) {
		return MD5Util.getMD5String(acount + password + "salt");
	}
	
	/**
	 * 登录密码
	 * @param acount
	 * @param password
	 * @return
	 */
	public static String getLoginPassWord(String acount, String password) {
		return MD5Util.getMD5String(acount + password + "salt");
	}
	
	/**
	 * pos机密码
	 * @param acount
	 * @param password
	 * @return
	 */
	public static String getPosPassWord(String acount, String password) {
		return MD5Util.getMD5String(password);
	}
}
