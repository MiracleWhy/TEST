package com.uton.carsokApi.filter;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.util.StringUtil;

/**
 * 登录验证拦截器
 * 
 * @author bing.cheng
 *
 */
public class SubUserAuthInterceptor implements MethodInterceptor {
  
	//TODO token和subToken 同时存在情况下 不做处理 （需要前端处理）
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
	  HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	 
	  //主账户 所有登录后的接口都能访问
	  String token = req.getHeader("token");
	  if (!StringUtil.isEmpty(token)) {
		  return invocation.proceed();
	  }
	  
	  //子账户只能访问登录后 加了注解的接口
	  String subToken = req.getHeader("subToken");
	  if (!StringUtil.isEmpty(subToken)) {
		  SubUserAuthority subUserAuth = invocation.getMethod().getAnnotation(SubUserAuthority.class);
		  if (subUserAuth != null && subUserAuth.validate()) {
			  return invocation.proceed();
		  }
	  }
	  return JSON.toJSONString(BaseResult.fail(UserErrorCode.UserPermissionDeniedErrorRetCode, UserErrorCode.UserPermissionDeniedErrorRetInfo));
  }

}
