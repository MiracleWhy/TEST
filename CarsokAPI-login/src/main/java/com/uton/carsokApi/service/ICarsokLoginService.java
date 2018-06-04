package com.uton.carsokApi.service;

import com.uton.carsokApi.model.CarsokLoginToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/22.
 */
public interface ICarsokLoginService {
    /**
     * 登录时记录token
     * @param account
     * @param token
     * @param loginTime
     * @return
     */
    int accountLogin(String account, String token, Date loginTime,String ipAddress,String addressMsg);

    /**
     * 登出时记录时间
     * @param token
     * @param loginOutTime
     * @return
     */
    int accountLoginOut(String token, Date loginOutTime);

    /**
     * 获取真实IP
     * @param request
     * @return
     */
    String getIp(HttpServletRequest request);

}
