package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.dao.CarsokLoginMapper;
import com.uton.carsokApi.service.ICarsokLoginService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/22.
 */
@Service
public class CarsokLoginServiceImpl implements ICarsokLoginService{

    public static CarsokLoginServiceImpl carsokLoginService;

    @PostConstruct
    public void init() {
        carsokLoginService = this;
    }

    public Logger logger = LoggerFactory.getLogger(CarsokLoginServiceImpl.class);

    @Autowired
    CarsokLoginMapper carsokLoginMapper;

    @Override
    public int accountLogin(String account, String token, Date loginTime,String ipAddress,String addressMsg) {
        try {
            int insert = carsokLoginService.carsokLoginMapper.insert(account,token,loginTime,ipAddress,addressMsg);
            logger.info("记录"+insert+"条信息，帐号："+account+"token："+token+"登录时间"+loginTime);
            return insert;
        }catch (Exception e){
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public int accountLoginOut(String token, Date loginOutTime) {
        try {
            int update = carsokLoginMapper.update(token,loginOutTime);
            logger.info("修改"+update+"条信息，token："+token+"登出时间"+loginOutTime);
            return update;
        }catch (Exception e){
            logger.error(e.getMessage());
            return 0;
        }
    }

    public class GetAddress implements Runnable{

        private String account;
        private String token;
        private String address;

        public GetAddress(){

        }

        public GetAddress(String account,String token,String address){
            this.account = account;
            this.token = token;
            this.address = address;
        }

        @Override
        public void run() {
            try{
                URL url = new URL( "http://ip.taobao.com/service/getIpInfo.php?ip=" + address);
                URLConnection conn =  url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
                String line = null;
                StringBuffer result = new StringBuffer();
                while((line = reader.readLine()) != null){
                    result.append(line);
                }
                reader.close();
                accountLogin(account,token,new Date(),address,result.toString());
            }catch (Exception e){
                logger.error("获取位置信息失败，帐号："+account+"，token："+token+"，ip："+address);
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
