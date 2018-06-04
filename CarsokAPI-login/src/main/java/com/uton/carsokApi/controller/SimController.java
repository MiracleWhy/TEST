package com.uton.carsokApi.controller;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.SalesForg;
import com.uton.carsokApi.model.SourceForgSum;
import com.uton.carsokApi.service.ActiveUserService;
import com.uton.carsokApi.util.HttpClient;
import com.uton.carsokApi.util.HttpClientUtil;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RunAs;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by SEELE on 2018/3/16.
 */
@Controller
public class SimController {


    @Autowired
    ActiveUserService activeUserService;



    @RequestMapping("/simLogin")
    @ResponseBody
    public BaseResult simLogin(HttpServletRequest request, SalesForg salesForg){
        try {
            activeUserService.SimLogin();

            //Thread thread = new Thread(new DoSimLogin(), "DoSimLogin");
            //thread.start();
        }catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }

        return BaseResult.success();

    }

    private class DoSimLogin implements Runnable{

        @Override
        public void run()
        {
            String domain="http://carsok.utonw.com/carsokApi";


            List<Acount> acounts = activeUserService.selectSimLogin();
            for(Acount acount:acounts)
            {
                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("account", acount.getAccount());
                    params.put("accountPassword", acount.getAccountPassword());
                    String result = HttpClient.sendPostRequestByJava(domain + "/login.do", JSONObject.toJSONString(params));
                    System.out.println(result);
                    JSONObject objResult = JSONObject.parseObject(result.split("`")[0]);
                    if(objResult.getString("retCode").equals("0000"))
                    {
                        JSONObject data =objResult.getJSONObject("data");
                        String token = data.getString("token");
                        System.out.println(token);

                        String result1 = HttpClient.sendPostRequestByJavaWithHeader(domain+"/getStoreInfo.do","",token);
                        System.out.println(result1);
                    }

                    Thread.sleep(((int)(Math.random()*2))*1000);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
