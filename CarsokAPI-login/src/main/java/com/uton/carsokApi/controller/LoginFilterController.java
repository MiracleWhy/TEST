package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.UserErrorCode;





/**
 * 
 * @ClassName: LoginFilterController 
 * @Description: TODO
 * @author: yanyong
 * @date: 2016年4月7日 下午8:21:05
 */
@Controller
@RequestMapping("/rest")
public class LoginFilterController {
  private final static Logger logger = Logger.getLogger(LoginFilterController.class);


  @RequestMapping(value = {"/loginCheckFail"})
  public @ResponseBody BaseResult loginCheckFail(HttpServletRequest request,
      HttpServletResponse response) {
    return BaseResult.fail(UserErrorCode.UserPermissionDeniedErrorRetCode,
        UserErrorCode.UserPermissionDeniedErrorRetInfo);
  }

  @RequestMapping(value = {"/tokenCheckFail"})
  public @ResponseBody BaseResult tokenCheckFail(HttpServletRequest request,
                                                 HttpServletResponse response) {
    return BaseResult.fail(UserErrorCode.ExpiredTokenErrorRetCode,
            UserErrorCode.ExpiredTokenErrorRetInfo);
  }

}
