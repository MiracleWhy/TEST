package com.uton.carsokApi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.uton.carsokApi.constants.Common;
import com.uton.carsokApi.controller.request.RequestAuthority;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.util.StringUtil;

/**
 * 
 * 登录过滤
 *
 */
public class LoginFilter implements Filter {
	private final static Logger logger = Logger.getLogger(LoginFilter.class);
	private static String[] whiteList;
	private static RedisTemplate redisTemplate;
	private final static int TIME = 3600 * 24;

	private final static boolean isSSO = true;
	private final static String[] undoSSOList = new String[]{
			"13521759156","15711133836"
	};

	public static void setRedisTemplate(RedisTemplate redisTemplate) {
		LoginFilter.redisTemplate = redisTemplate;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String whiteListStr = filterConfig.getInitParameter("whiteList");
		if (!StringUtil.isEmpty(whiteListStr)) {
			whiteList = whiteListStr.split("\\|");
		}

	}

	/**
	 * @param phone
	 * @return true:需要check false 不需要check
	 */
	private boolean isSSOCheck(String phone) {
		if (!isSSO) {
			return false;
		}

		for (String ucPhone : undoSSOList) {
			if (ucPhone.equals(phone)) {
				return false;
			}
		}
		return true;
	}


	/**
	 * 过滤
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String url = req.getRequestURI().toString();
		if(url.endsWith("ceshi.do")||url.endsWith("messagePush.do")||url.endsWith(".css")||url.endsWith(".js")||url.endsWith(".png")
				||url.indexOf("customer")>-1||url.indexOf("CustomMessagePush")>-1||url.indexOf("lkl")>-1
				||url.indexOf("pay/callBack")>-1||url.indexOf("TenureCustomer")>-1||url.indexOf("selectAppRecentInfo.do")>-1
				||url.indexOf("AcquisitionCar")>-1||url.indexOf("carLogo")>-1||url.indexOf("queryContractByCarId")>-1||url.indexOf("carContractByCarId")>-1

				||url.indexOf("dailyReport")>-1||url.indexOf("bussinesPage")>-1||url.indexOf("deleteInfo")>-1||url.indexOf("hasDoTaskInfo")>-1||url.indexOf("searchUserList.do")>-1||url.indexOf("sear.do")>-1||url.indexOf("Allchild.do")>-1
				||url.indexOf("oneMessage")>-1||url.indexOf("selectSaleCar")>-1||url.indexOf("updateChild")>-1
				||url.indexOf("dailyReport")>-1||url.indexOf("bussinesPage")>-1||url.indexOf("deleteInfo")>-1

				||url.indexOf("dailyReport")>-1||url.indexOf("bussinesPage")>-1||url.indexOf("deleteInfo")>-1
				||url.indexOf("storeOrAcquisitionCar")>-1||url.indexOf("getChildInfoById")>-1 || url.indexOf("datumManage")>-1
				||url.indexOf("AcquisitionCar/followUpShare.do")>-1||url.indexOf("AcquisitionCar/followUpShareSave.do")>-1||url.indexOf("AcquisitionCar/selectMsg.do")>-1
			    ||url.endsWith("queryContractList.do")||url.endsWith("newcarMessagePush.do")||url.endsWith("getChildInfoByCustMobile.do")
			    ||url.endsWith("queryContractList.do")||url.endsWith("getChildInfoByCustMobile.do")||url.endsWith("getManagerInsideByCarId.do")||url.endsWith("getSellerInfo.do")
				||url.indexOf("transfer")>-1||url.indexOf("utonwGK")>-1 ||url.indexOf("ally")>-1 || url.indexOf("carsokQuote")>-1||url.indexOf("ActiveUser")>-1||url.indexOf("updateMerchantInfo")>-1||url.indexOf("updatePasswordByPmsAccountId")>-1)

		{
			chain.doFilter(request, response);
			return;
		}
		logger.info("LoginFilter doFilter url:" + url);
		if (whiteList == null) {
			chain.doFilter(request, response);
			return;
		}
		for (String whiteUrl : whiteList) {
			if (url.contains(whiteUrl)) {
				chain.doFilter(request, response);
				return;
			}
		}

		String token = req.getHeader("token");
		logger.info("LoginFilter doFilter token:" + token);
		String subToken = req.getHeader("subToken");
		// 主账户
		if (!StringUtil.isEmpty(token)) {
			ValueOperations<String, Acount> userValueOp = redisTemplate.opsForValue();
			Acount acount = userValueOp.get(token);
			if (acount != null) {
				RequestAuthority requestAuthority = (RequestAuthority) redisTemplate.opsForValue().get(Common.USER+acount.getAccount());
				if (requestAuthority != null) {
					//更新访问时间戳
					requestAuthority.setRefreshTime(System.currentTimeMillis());
					//回写进redis
					redisTemplate.opsForValue().set(Common.USER + acount.getAccount(), requestAuthority);
					//TODO:SSO影响较大，暂时注释掉
					if(isSSOCheck(acount.getAccount())&&!requestAuthority.getToken().equals(token)) {
						req.getRequestDispatcher("/rest/tokenCheckFail.do").forward(request, response);
						return;
					}
				} else {
					requestAuthority = new RequestAuthority();
					requestAuthority.setToken(token);
					//更新访问时间戳
					requestAuthority.setRefreshTime(System.currentTimeMillis());
					//回写进redis
					redisTemplate.opsForValue().set(Common.USER + acount.getAccount(), requestAuthority);
				}
				chain.doFilter(request, response);
				return;
			}
		}
		// 子账户
		else if (!StringUtil.isEmpty(subToken)) {
			ValueOperations<String, ChildAccount> subUserValueOp = redisTemplate.opsForValue();
			ChildAccount acount = subUserValueOp.get(subToken);
			if (acount != null) {
				RequestAuthority requestAuthority = (RequestAuthority) redisTemplate.opsForValue().get(Common.USER+acount.getAlias());
				if (requestAuthority != null) {
					//更新访问时间戳
					requestAuthority.setRefreshTime(System.currentTimeMillis());
					//回写进redis
					redisTemplate.opsForValue().set(Common.USER + acount.getAlias(), requestAuthority);
					//TODO:SSO影响较大，暂时注释掉
					if(isSSOCheck(acount.getChildAccountMobile())&&!requestAuthority.getToken().equals(subToken)) {
						req.getRequestDispatcher("/rest/tokenCheckFail.do").forward(request, response);
						return;
					}
				} else {
					requestAuthority = new RequestAuthority();
					requestAuthority.setToken(token);
					//更新访问时间戳
					requestAuthority.setRefreshTime(System.currentTimeMillis());
					//回写进redis
					redisTemplate.opsForValue().set(Common.USER + acount.getAlias(), requestAuthority);
				}

				chain.doFilter(request, response);
				return;
			}
		}
		else {
			//nothing
		}
		req.getRequestDispatcher("/rest/loginCheckFail.do").forward(request, response);

	}

	public void destroy() {

	}

}
